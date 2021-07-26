package system.name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class GetMFInfo {


    private static final Logger LOGGER = LoggerFactory.getLogger("GetMFInfo.class");
    public static void main(String[] args) {


        String path = "C:\\Users\\EENHENI\\.m2\\repository\\org\\testng\\testng\\6.14.8\\testng-6.14.8.jar";

        String relativePath  = "/META-INF/MANIFEST.MF";

        Properties properties = new Properties();

        getManiFestFromJar(path);

    }

    public void getInfo() throws IOException {

        Properties properties = new Properties();

        String relativePath  = "/META-INF/MANIFEST.MF";

        InputStream resourceAsStream = this.getClass().getResourceAsStream("/" + relativePath);
        properties.load(resourceAsStream);


    }

    public static String getManiFestFromJar(String path) {

        String version = null;
        try {
            JarFile jf = new JarFile(path);

            Manifest manifest = jf.getManifest();

            Attributes mainAttributes = manifest.getMainAttributes();
//            logger.info("mainAttributes are {} ", mainAttributes.toString());


            System.out.println(mainAttributes);

            version = mainAttributes.getValue("Bundle-Version");
            String description = mainAttributes.getValue("Bundle-Description");

//            logger.info("version is {} ", version);
            System.out.println("version: " + version);
            System.out.println("Bundle-Description: " + description);

        } catch (IOException e) {
            LOGGER.info("Cannot find jar file - {} ", path);
        }

        return version;
    }
}
