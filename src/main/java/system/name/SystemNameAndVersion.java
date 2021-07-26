package system.name;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SystemNameAndVersion {
    public static void main(String[] args) {

//        String userName = System.getProperty("user.name");
//        System.out.println(userName);
//
//        String name = System.getProperty("os.name");
//        String version = System.getProperty("os.version");
//        System.out.println(name);
//        System.out.println(version);

        String hostname = getHostname();
        System.out.println("host name is " + hostname);


        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));


        stringToFile(hostname, format + ".txt",  System.getProperty("user.home"));

//        String format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
//        stringToFile(hostname, format + ".txt",  System.getProperty("user.home"));
//        stringToFile(hostname, format + ".txt", ".");
    }


    public static String getHostname() {

        String hostname = System.getenv("HOSTNAME");
        if (hostname != null && !hostname.isEmpty()) {
            return hostname;
        }

        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
//            LOGGER.warn("cannot resolve local hostname", e);
            System.out.println("Can not resolve local hostname " + e);
        }

        return "localhost";
    }


    public static void stringToFile(String string, String fileName, String dirPath) {
        String filePath = dirPath + File.separator + fileName;
        boolean directory = createDirectory(dirPath);
        System.out.println("path is " + filePath);
//        logger.info("Path: {} ", filePath);
        if (directory) {
            try (FileWriter fileWritter = new FileWriter(filePath);
                 BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                bufferWritter.write(string);
            } catch (IOException e) {
//                logger.error("Cannot write string to file {} ", filePath);
                System.out.println("Cannot write string to file .");
            }
        }

    }

    public static boolean createDirectory(String dir) {

        System.out.println(" dir is " + dir);
        File file = new File(dir);
        boolean isCreateDir = false;
        if (!file.exists()) {
            if (file.mkdirs()) {
                isCreateDir = true;
            }
        } else {
            isCreateDir = true;
        }
        return isCreateDir;
    }

}
