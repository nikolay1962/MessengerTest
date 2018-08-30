package my.messages;

import java.time.LocalDateTime;

public class User {

    private String email;
    private String name;
    private int age;
    private String password;
    private LocalDateTime lastLoginTime;

    public User(String email, String name, int age, String password) {
        this.email = email;
        this.name = name;
        this.age = age;
        this.password = password;
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

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("email='").append(email).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", password='").append(password).append('\'');
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append('}');
        return sb.toString();
    }

}
