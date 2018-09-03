package my.messages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Properties;

public class UserServices {

    IOUtils ioUtils;

    private static final String filePath = "C:\\TMP\\Messanger\\";

    public UserServices() {
        this.ioUtils = new IOUtils();
    }

    public UserServices(IOUtils ioUtils) {
        this.ioUtils = ioUtils;
    }

    public User getUser(String email, String password) throws IOException {
        User user = null;
        //read file
        Properties config = new Properties();
        Path userData = Paths.get(filePath + email);
        try (InputStream stream = Files.newInputStream(userData)) {

            config.load(stream);
        }

        printToSystemOutput(config);

        //get fields from file
        if (password.equals(config.getProperty("password"))) {
            user = new User(config.getProperty("email"),
                    config.getProperty("name"),
                    Integer.parseInt(config.getProperty("age")),
                    config.getProperty("password"));
        }
        //create new user
        return user;
    }

    private void printToSystemOutput(Properties config) throws IOException {
        config.store(System.out, "Loaded properties:");
    }

    public boolean saveData(User user) {
        boolean returnValue = true;
        if (user == null) {
            return false;
        }

        Path userFile = Paths.get(filePath + user.getEmail());
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(filePath + user.getEmail());
            LocalDateTime currentMoment = LocalDateTime.now();

            // set the properties value
            prop.setProperty("email", user.getEmail());
            prop.setProperty("name", user.getName());
            prop.setProperty("password", user.getPassword());
            prop.setProperty("age", String.valueOf(user.getAge()));
            prop.setProperty("lastLoginTime", currentMoment.toString());

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        try (BufferedWriter writer = Files.newBufferedWriter(userFile)) {
//            StringBuilder sb = new StringBuilder("");
//            sb.append("email=").append(user.getEmail()).append('\n');
//            sb.append("name=").append(user.getName()).append('\n');
//            sb.append("age=").append(user.getAge()).append('\n');
//            sb.append("password=").append(user.getPassword()).append('\n');
//            sb.append("lastLoginTime=").append(user.getLastLoginTime());
//            sb.append('\n');
//            writer.write(sb.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            returnValue = false;
//        }

        return returnValue;
    }

    public boolean isUserExists(String email) {
        Path userFile = Paths.get(filePath + email);
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

    public User addUser() {
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

        return user;
    }

    public User login() throws IOException {
        String email = ioUtils.getValidEmail("Enter your email:");

        if (email == null) {
            return null;
        }
        String password = ioUtils.getNotEmptyString("Enter password:");

        User user = getUser(email, password);

        return user;
    }

    public void sendMessageToUser(User currentUser) {
        String recepient = getValidUserEmail(currentUser.getEmail(), "Enter recepients email:");

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
}
