package my.messages;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class UserServices {

    IOUtils ioUtils;

    final static String[] PREFIX_SUFFIX = {"to_", ".txt"};

    public static  final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public UserServices() {
        this.ioUtils = new IOUtils();
    }

    public UserServices(IOUtils ioUtils) {
        this.ioUtils = ioUtils;
    }

    public void getUser(String email, String password) throws IOException {
        User user = null;
        //read file
        Properties config = ioUtils.getProperties(email);

        //get fields from file
        if (config != null && password.equals(config.getProperty("password"))) {
            user = new User(config.getProperty("email"),
                    config.getProperty("name"),
                    Integer.parseInt(config.getProperty("age")),
                    config.getProperty("password"));
            user.setLastLogoutTime(LocalDateTime.parse(config.getProperty("lastLogoutTime"), FORMATTER));
            user.setLastTimeOfMessageGet(Long.parseLong(config.getProperty("lastTimeOfMessageGet")));
        }
        //create new user
        if (user != null) {
            runUser(user);
        }
    }

    public boolean saveData(User user) {
        if (user == null) {
            return false;
        }

        return ioUtils.saveUserData(user);
    }

    public boolean isUserExists(String email) {
        Path userFile = Paths.get(email);
        return ioUtils.fileExists(userFile);
    }

    public User newUser(String email, String password, String name, int age) {
        User user = new User(email, name, age, password);
        if (saveData(user)) {
            return user;
        } else {

            return null;
        }
    }

    public void addUser() {
        System.out.println("Please, enter your personal data:");
        boolean proceed = false;
        User user = null;
        String email = null;
        while (true) {
            email = ioUtils.getValidEmail("Enter your email:");
            if (email == null) {
                break;
            }
            boolean exists = isUserExists(email);
            if (exists) {
                ioUtils.writeMessage(email + " is already registered in a system.");
            } else {
                proceed = true;
                break;
            }
        }

        if (proceed) {
            String password = ioUtils.getNotEmptyString("Enter password:");
            String name = ioUtils.getNotEmptyString("Enter your name:");
            int age = ioUtils.getPositiveInteger("Enter your age:");

            user = newUser(email, password, name, age);
        }

        if (user != null) {
            runUser(user);
        }
    }

    public User login() throws IOException {
        String email = ioUtils.getValidEmail("Enter your email:");

        if (email == null) {
            return null;
        }
        String password = ioUtils.getNotEmptyString("Enter password:");

        User user = getUser(email, password);
        if (user != null) {
            ioUtils.writeMessage("Hello mr." + user.getName());
            ioUtils.writeMessage("You have not been here since " + user.getlastLogoutTime().format(FORMATTER));
        }

        startGettingMessages(user);

        return user;
    }

    public void logout(User user) {
        user.getMessageChecker().terminate();
        try {
            user.getThreadMessageChecker().join();
        } catch (InterruptedException e) {
            ioUtils.writeMessage("Message Checker tread finished with Exception");
        }
        ioUtils.saveUserData(user);
    }

    void startGettingMessages(User user) {
        MessageChecker messageChecker = new MessageChecker();
        messageChecker.setIoUtils(ioUtils);
        messageChecker.setUser(user);
        user.setMessageChecker(messageChecker);

        Thread thread = new Thread(messageChecker);
        thread.start();
        user.setThreadMessageChecker(thread);
    }

    public void sendMessageToUser(User currentUser) {
        String recepient = getValidUserEmail("Enter recepients email:");
//        String recepient = getValidUserEmail(currentUser.getEmail(), "Enter recepients email:");

        if (recepient == null) {
            ioUtils.writeMessage("Unable to get Recepients name");
            return;
        }

        String message = ioUtils.getNotEmptyString("Enter your message:");

        if (message == null) {
            ioUtils.writeMessage("Message was NOT sent to " + recepient);
            return;
        }

        boolean result = ioUtils.sendMessage(currentUser.getEmail(), recepient, message);
        if (result) {
            ioUtils.writeMessage("Message was sent to " + recepient);
        } else {
            ioUtils.writeMessage("Message was NOT sent to " + recepient);
        }
    }

    private String getValidUserEmail(String currentUserEmail, String message) {

        while (true) {
            String email = ioUtils.getValidEmail(message);
            if (email == null) {
                return null;
            }

            if (currentUserEmail.equals(email)) {
                continue;
            }
            if (!isUserExists(email)) {
                ioUtils.writeMessage("No such user:" + email);
                continue;
            } else {
                return email;
            }
        }
    }

    private String getValidUserEmail(String message) {

        return getValidUserEmail("allowed to yourself", message);
    }

    private void runUser(User user) {

        boolean proceed = true;
        String choice = "";

        while (proceed) {
            printMenu(user);
            choice = ioUtils.getInputFromUser();
            switch (choice) {
                case "1":
                    proceed = false;
                    if (user != null) {
                        logout(user);
                    }
                    user = null;
                    break;
                case "2":
                    if (user == null) {
                        ioUtils.writeMessage("Please, login first.");
                    } else {
                        sendMessageToUser(user);
                    }
                    break;
                case "3":
                    if (user == null) {
                        ioUtils.writeMessage("Please, login first.");
                    } else {
                        ioUtils.saveUserData(user);
                    }
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
            }
        }
    }

    private static void printMenu(User currentUser) {
        String whoAmI = currentUser == null ? "Unknown user" : currentUser.getName();
        System.out.println("1 - Logout");
        System.out.println("2 - Send Message");
        System.out.println("3 - Save data");
        System.out.println("4 - Join Chat");
        System.out.println("5 - Start Chat");
        System.out.println("6 - Remove User");
        System.out.println("Please, enter your choice, mr." + whoAmI);
    }
}
