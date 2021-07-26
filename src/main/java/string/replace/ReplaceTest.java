package string.replace;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReplaceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger("ReplaceTest.class");
    public static void main(String[] args) {


        String versionFile = "mje-version.txt";
        String property = "mje.version";
        String logName = "MJE";
        logVersion(versionFile, property, logName, "");
    }

    public static String logVersion(String versionFile, String property, String logName, String site) {
        String version = "unknown";
        try (InputStream is = ReplaceTest.class.getResourceAsStream("/" + versionFile)) {
            version = IOUtils.toString(is, "UTF-8");
            version = "1.8.7623-20200224055335-BLACK";
        } catch (IOException | NullPointerException e) {
            LOGGER.warn("Failed to read {}", versionFile, e);
        }
//        Log.info("Initializing MSRAN JCAT extension. {} version {}", logName, version);
        Properties systemProps = System.getProperties();
        systemProps.setProperty(property, version);
        version = version.replaceAll(property + "=", "");
//        Log.info("{} version: {}", logName, version);
        version = version.replaceAll("\\s+", "");


        System.out.println(version);
        return version;
    }
}
