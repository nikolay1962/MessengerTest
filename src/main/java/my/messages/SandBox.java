package my.messages;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SandBox {

    public static void main(String[] args) {
        System.out.println("milis:" + System.currentTimeMillis());
        System.out.println(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        System.out.println(Instant.ofEpochMilli(1536092965124l).atZone(ZoneId.systemDefault()).toLocalDateTime().toString());
        System.out.println(LocalDateTime.parse("2018-09-04T23:29:25.124"));
//        long lastLogoutMillis = user.getlastLogoutTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
//        String timeOfLastGotMessage = Instant.ofEpochMilli(lastMessageGet).atZone(ZoneId.systemDefault()).toLocalDate().toString();
    }
}
