package my.messages;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Scanner;

public class IOUtils {

    Scanner scanner;

    private static final String filePath = "C:\\TMP\\Messanger\\";

    public IOUtils() {
        this.scanner = new Scanner(System.in);
    }

    public boolean fileExists(String filename) {
        Path path = Paths.get(filePath + filename);
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

    public boolean sendMessage(String currentUserEmail, String recepient, String message) {
        String filename = currentUserEmail + "-to-" + recepient + ".txt";
        if (!fileExists(filename) && !createFile(filename)) {
            return false;
        }

        try(FileWriter fw = new FileWriter(filePath + filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(LocalDateTime.now().toString());
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
}
