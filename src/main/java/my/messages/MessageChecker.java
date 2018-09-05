package my.messages;

public class MessageChecker implements Runnable {

    private volatile boolean proceed = true;

    private User user;
    private IOUtils ioUtils;

    @Override
    public void run() {
        while (proceed) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                proceed = false;
            }
            long lastMessageGet = user.getLastTimeOfMessageGet();
            boolean haveNewMessages = ioUtils.checkIfHaveNewMessages(user.getIncomingMessages(), lastMessageGet);

            if (haveNewMessages) {
                System.out.println("Have new Messages!!!!!!!!");
                ioUtils.displayToUser(user, lastMessageGet);
            }
        }
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setIoUtils(IOUtils ioUtils) {
        this.ioUtils = ioUtils;
    }

    public void terminate() {
        this.proceed = false;
    }
}
