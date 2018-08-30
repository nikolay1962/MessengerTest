package my.messages;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        User currentUser = null;

        boolean proceed = true;
        String choice = "";

        while (proceed) {
            printMenu(currentUser);
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    proceed = false;
                    break;
                case "2":
                    if (currentUser == null) {
                        currentUser = newUser();

                    } else {
                        System.out.println(currentUser.getName() + ", please logout first.");
                    }
                    break;
                case "3":
                    if (currentUser == null) {
                        currentUser = getUser();

                    } else {
                        System.out.println(currentUser.getName() + ", please logout first.");
                    }
                    break;
                case "4":
                    break;
                case "5":
                    currentUser = null;
                    break;
                case "6":
                    break;
            }
        }

    }

    private static User newUser() {
        System.out.println("Please, enter your personal data:");
        boolean proceed = false;
        User user = null;
        String email = null;
        while (true) {
            System.out.println("Enter your email:");

            email = scanner.nextLine();
            if (email == null || email.isEmpty()) {
                break;
            }
            if (!email.contains("@")) {
                continue;
            }
            boolean exists = UserServices.isUserExists(email);
            if (exists) {
                System.out.println(email + " is already registered in a system.");
            } else {
                proceed = true;
                break;
            }
        }

        if (proceed) {
            String password = getNotEmptyString("Enter password:");
            String name = getNotEmptyString("Enter your name:");
            int age = getPositiveInteger("Enter your age:");

            user = UserServices.newUser(email, password, name, age);
        }

        return user;
    }

    private static int getPositiveInteger(String request) {
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

    private static String getNotEmptyString(String request) {
        String input = "";
        while (input.isEmpty()) {
            System.out.print(request);
            input = scanner.nextLine();
        }
        return input;
    }

    private static void printMenu(User currentUser) {
        String whoAmI = currentUser == null ? "Unknown user" : currentUser.getName();
        System.out.println("1 - Exit");
        System.out.println("2 - Sign Up (new user)");
        System.out.println("3 - Login");
        System.out.println("4 - Save data");
        System.out.println("5 - Logout");
        System.out.println("6 - Remove User");
        System.out.println("Please, enter your choice, mr." + whoAmI);
    }

    private static User getUser() throws IOException {

        System.out.print("Please enter email:");
        String email = scanner.nextLine();

        System.out.print("Please enter password:");
        String password = scanner.nextLine();

        return UserServices.getUser(email, password);

    }
}
