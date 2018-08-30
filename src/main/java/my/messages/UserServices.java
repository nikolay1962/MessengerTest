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

    private static final String filePath = "C:\\TMP\\Messanger\\";

    public static User getUser(String email, String password) throws IOException {
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

    private static void printToSystemOutput(Properties config) throws IOException {
        config.store(System.out, "Loaded properties:");
    }

    public static boolean saveData(User user) {
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

    public static boolean isUserExists(String email) {
        Path userFile = Paths.get(filePath + email);
        return Files.exists(userFile);
    }

    public static User newUser(String email, String password, String name, int age) {
        User user = new User(email, name, age, password);
        if (saveData(user)) {
            return user;
        } else {

            return null;
        }
    }
}
