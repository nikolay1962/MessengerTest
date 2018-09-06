package my.messages;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class IOUtils {

    Scanner scanner;

    public IOUtils() {
        this.scanner = new Scanner(System.in);
    }

    public boolean fileExists(String filename) {
        Path path = Paths.get(filename);
        return fileExists(path);
    }

    public boolean fileExists(Path path) {
        return Files.exists(path);
    }

    public void writeMessage(String message) {
        System.out.println(message);
    }

    public String readNextLine() {
        return scanner.nextLine();
    }

    public int getPositiveInteger(String request) {
        int integer = 0;
        while (integer < 1 || integer > 100) {
            System.out.print(request);
            try {
                integer = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                integer = 0;
            }
        }
        return integer;
    }

    public String getNotEmptyString(String request) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(request);
            input = scanner.nextLine();
        }
        return input;
    }

    public String getValidEmail(String message) {

        while (true) {
            writeMessage(message);

            String email = readNextLine();
            if (email == null || email.isEmpty()) {
                return null;
            }
            if (!email.contains("@")) {
                continue;
            }

            return email;
        }
    }

    public boolean sendMessage(String currentUserEmail, String filename, String message) {
//        String filename = UserServices.PREFIX_SUFFIX[0] + recepient + UserServices.PREFIX_SUFFIX[1];
        if (!fileExists(filename) && !createFile(filename)) {
            return false;
        }

        try(FileWriter fw = new FileWriter(filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.print(LocalDateTime.now().toString() + "; from " + currentUserEmail + "; ");
            out.println(message);

        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private boolean createFile(String filename) {
        Path filePath = Paths.get(filename);
        try {
            Files.createFile(filePath);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Properties getProperties(String email) {
        Properties config = new Properties();
        Path userData = Paths.get(email);
        try (InputStream stream = Files.newInputStream(userData)) {
            config.load(stream);
        } catch (IOException e) {
            return null;
        }

        return config;
    }

    public boolean saveUserData(User user) {

        Path userFile = Paths.get(user.getEmail());
        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream(user.getEmail());
            LocalDateTime currentMoment = LocalDateTime.now();

            // set the properties value
            prop.setProperty("email", user.getEmail());
            prop.setProperty("name", user.getName());
            prop.setProperty("password", user.getPassword());
            prop.setProperty("age", String.valueOf(user.getAge()));
            prop.setProperty("lastLogoutTime", currentMoment.format(UserServices.FORMATTER));
            prop.setProperty("lastTimeOfMessageGet", Long.toString(user.getLastTimeOfMessageGet()));

            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
            return false;
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    public boolean checkIfHaveNewMessages(String incomingMessages, long lastMessageMillis) {
        File file = new File(incomingMessages);

        return file.lastModified() > lastMessageMillis;
    }

    public void displayToUser(Chat chat) {

        //read all lines from message file
        Path messages = Paths.get(chat.getMessageFile());
        // variable to compare times;

        String timeOfLastPrintedMessage = Instant.ofEpochMilli(chat.getLastTimeOfMessageGet()).atZone(ZoneId.systemDefault()).toLocalDateTime().toString();

        try {
            List<String> lines = Files.readAllLines(messages);

            boolean printChatName = true;

            for (String line : lines) {
                if (line.indexOf(';') > 0) {
                    String timeOfMessage = line.substring(0, line.indexOf(';'));
                    if (timeOfLastPrintedMessage.compareTo(timeOfMessage) < 0) {
                        //display to user.
                        if (printChatName) {
                            printChatName = false;
                            writeMessage("<++++++++++ " + chat.getName() + " ++++++++++>");
                        }
                        writeMessage('\t' + line);
                        timeOfLastPrintedMessage = timeOfMessage;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        //change lastTimeOfMessageGet in user data
        chat.setLastTimeOfMessageGet(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }

    public String getInputFromUser() {
        return scanner.nextLine();
    }

}
