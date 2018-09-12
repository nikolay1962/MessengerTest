package my.messages;

import java.io.IOException;

public class LoginView implements MyChatViewsInterface {

    private UserServices userServices;

    private IOUtils ioUtils;

    private boolean proceed;

    public LoginView() {

        this.userServices = new UserServices();
        this.ioUtils = new IOUtils();
        this.proceed = true;
    }

    @Override
    public void show(User user) {
        while (this.proceed) {
            printMenu(user);
            String choice = ioUtils.getInputFromUser();
            processUserInput(choice);
        }
    }

    @Override
    public void processUserInput(String choice) {
        switch (choice) {
            case "1":
                this.proceed = false;
                break;

            case "2":
                userServices.addUser();
                break;

            case "3":
                try {
                    userServices.login();
                } catch (IOException e) {
                    ioUtils.writeMessage("LoginView: IOException while trying to login.");
                }
                break;
        }
    }


    private void printMenu(User currentUser) {
//        String whoAmI = currentUser == null ? "Unknown user" : currentUser.getName();
        System.out.println("1 - Exit");
        System.out.println("2 - Sign Up (new user)");
        System.out.println("3 - Login");
        System.out.println("Please, enter your choice, mr. Unknown");
    }

}
