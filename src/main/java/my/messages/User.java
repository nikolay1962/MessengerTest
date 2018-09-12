package my.messages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class User {

    private UserServices userServices;

    private String email;
    private String name;
    private int age;
    private String password;
    private LocalDateTime lastLogoutTime;
    private volatile long lastTimeOfMessageGet;
    private String incomingMessages;
    private List<Chat> chats;

    public User(String email, String name, int age, String password, long lastTimeOfMessageGet) {
        this.userServices = new UserServices();
        this.email = email;
        this.name = name;
        this.age = age;
        this.password = password;
        this.lastTimeOfMessageGet = lastTimeOfMessageGet;
        this.incomingMessages = userServices.messageFileName(email);
        this.chats = new ArrayList<>();
        Chat userMessages = new Chat("Personal for " + name, incomingMessages, lastTimeOfMessageGet);
        chats.add(userMessages);
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

    public void setLastLogoutTime(LocalDateTime lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public long getLastTimeOfMessageGet() {
        return lastTimeOfMessageGet;
    }

    public void setLastTimeOfMessageGet(long lastTimeOfMessageGet) {
        this.lastTimeOfMessageGet = lastTimeOfMessageGet;
    }

    public List<Chat> getChats() {
        return chats;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", password='").append(password).append('\'');
        sb.append(", lastLogoutTime=").append(lastLogoutTime.toString());
        sb.append('}');
        return sb.toString();
    }

}
