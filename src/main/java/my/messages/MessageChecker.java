package my.messages;

public class MessageChecker implements Runnable {

    private volatile boolean proceed = true;

    private Chat chat;
    private IOUtils ioUtils;

    @Override
    public void run() {
        String fileMessages = chat.getMessageFile();
        while (proceed) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                proceed = false;
            }
            long lastMessageGet = chat.getLastTimeOfMessageGet();
            boolean haveNewMessages = ioUtils.checkIfHaveNewMessages(fileMessages, lastMessageGet);

            if (haveNewMessages) {
                ioUtils.displayToUser(this.chat);
            }
        }
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setIoUtils(IOUtils ioUtils) {
        this.ioUtils = ioUtils;
    }

    public void terminate() {
        this.proceed = false;
    }
}
