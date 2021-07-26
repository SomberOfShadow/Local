package system.property;

import java.io.File;

public class GetDirectory {
    public static void main(String[] args) {

        String property = System.getProperty("user.home");
        System.out.println(property);
        String userHomeDir = System.getProperty("user.home") + File.separator + "error";
        File file = new File(userHomeDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        System.out.println(userHomeDir);
    }
}
