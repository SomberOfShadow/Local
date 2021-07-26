package iostream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ReadFromStream {

//    public final static Log LOGGER = LogFactory.getLog(ReadFromStream.class);
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFromStream.class);
    public static void main(String[] args) {

        LOGGER.debug("first time!");
        getInfoFromFile();
        LOGGER.debug("second time!");
        getInfoFromFile();

    }

    public static void getInfoFromFile(){
        String versionFile  = "version.txt";
        String version;
        try (InputStream is = ReadFromStream.class.getResourceAsStream("/" + versionFile)) {
//            version = IOUtils.toString(is, "UTF-8");
//            String separator = System.getProperty("line.separator");
//            boolean equals = separator.equals("\r\n");
//            LOGGER.info(equals);
//            LOGGER.info("separator is " + separator);
//            boolean contains = version.contains("\n");
//            LOGGER.info(contains);
//            String[] split = version.split("\n");
//            LOGGER.info("version is " + version);
//            LOGGER.info("mje version is " + split[0]);
//            String s = split[1].isEmpty() ? "unknown" : split[1];
//            if (!split[1].isEmpty()) {
//                LOGGER.info("product number is " + (split[1].isEmpty() ? "unknown" : split[1]));
//            }

           String versions[] = IOUtils.toString(is, "UTF-8").split("\n");

           LOGGER.info("fisrt line: {}.", versions[0]);
           LOGGER.info("second line: {}.", versions[1]);
        } catch (IOException e) {
            LOGGER.warn("Failed to read {}", versionFile);
        }
    }

}
