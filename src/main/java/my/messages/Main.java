package my.messages;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        User currentUser = null;

        UserServices userServices = new UserServices();

        IOUtils ioUtils = new IOUtils();

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
                        userServices.addUser();

                    } else {
                        System.out.println(currentUser.getName() + ", please logout first.");
                    }
                    break;
                case "3":
                    if (currentUser == null) {
                        currentUser = userServices.login();

                    } else {
                        System.out.println(currentUser.getName() + ", please logout first.");
                    }
                    break;
            }
        }
    }

    private static void printMenu(User currentUser) {
        String whoAmI = currentUser == null ? "Unknown user" : currentUser.getName();
        System.out.println("1 - Exit");
        System.out.println("2 - Sign Up (new user)");
        System.out.println("3 - Login");
        System.out.println("Please, enter your choice, mr." + whoAmI);
    }

}
