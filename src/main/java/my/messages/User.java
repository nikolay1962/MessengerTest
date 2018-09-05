package my.messages;

import java.time.LocalDateTime;

public class User {



    private String email;
    private String name;
    private int age;
    private String password;
    private LocalDateTime lastLogoutTime;
    private volatile long lastTimeOfMessageGet;
    private String incomingMessages;
    private MessageChecker messageChecker;
    private Thread threadMessageChecker;

    public User(String email, String name, int age, String password) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.password = password;
        incomingMessages = UserServices.PREFIX_SUFFIX[0] + email + UserServices.PREFIX_SUFFIX[1];
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getlastLogoutTime() {
        return lastLogoutTime;
    }

    public String getIncomingMessages() {
        return incomingMessages;
    }

    public void setLastLogoutTime(LocalDateTime lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public void setMessageChecker(MessageChecker messageChecker) {
        this.messageChecker = messageChecker;
    }

    public MessageChecker getMessageChecker() {
        return messageChecker;
    }

    public void setThreadMessageChecker(Thread threadMessageChecker) {
        this.threadMessageChecker = threadMessageChecker;
    }

    public Thread getThreadMessageChecker() {
        return threadMessageChecker;
    }

    public long getLastTimeOfMessageGet() {
        return lastTimeOfMessageGet;
    }

    public void setLastTimeOfMessageGet(long lastTimeOfMessageGet) {
        this.lastTimeOfMessageGet = lastTimeOfMessageGet;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", password='").append(password).append('\'');
        sb.append(", lastLogoutTime=").append(lastLogoutTime);
        sb.append('}');
        return sb.toString();
    }

}
