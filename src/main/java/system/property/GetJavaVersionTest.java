package system.property;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetJavaVersionTest {
    public final  static Log LOGGER = LogFactory.getLog(GetJavaVersionTest.class);
    public static void main(String[] args) {

        String javaVersion = System.getProperty("java.version");//1.8.0_202
        LOGGER.info("java version is " + javaVersion);


        String specificJavaVersion = System.getProperty("java.specification.version");//1.8
        LOGGER.info("specific java version is " + specificJavaVersion);
    }
}
