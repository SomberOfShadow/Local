package pom;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class GetPomTest {
    public static void main(String[] args) {
        getPom();
    }

    public static void getPom() {
        String userDir = System.getProperty("user.dir");
        System.out.println("userDir is " + userDir);

        String pomPath = userDir + File.separator + "pom.xml";

        System.out.println("pom path is " + pomPath);

        // 同样适应test repo jar的情形
        // 本地和CI

        getConnectTimeout();
    }


    public static void getConnectTimeout() {
        try {
            URL url = new URL("http://www.example.com");
            URLConnection conn = url.openConnection();
            System.out.println(String.format("connection timeout: %d", conn.getConnectTimeout()));
            System.out.println(String.format("read timeout: %d", conn.getReadTimeout()));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
