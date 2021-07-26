package log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class CommonLogTest {

//    static {
//        System.setProperty("org.apache.commons.logging.Log",
//                "org.apache.commons.logging.impl.NoOpLog");
//    }
//    private final static Log logger = LogFactory.getLog(CommonLogTest.class);

    public static void main(String[] args) {

//        System.setProperty("org.apache.commons.logging.Log",
//                "org.apache.commons.logging.impl.NoOpLog");
//        Log logger = LogFactory.getLog(CommonLogTest.class);
//        logger.info("It is just commons logging test before closing.");

        // close Apache commons logging
//        System.setProperty("org.apache.commons.logging.Log",
//                "org.apache.commons.logging.impl.NoOpLog");
//        logger.info("It is just commons logging test after closing.");

        System.setProperty("org.apache.commons.logging.Log",
                "org.apache.commons.logging.impl.NoOpLog");
        CommonLog commonLog = new CommonLog();
//        Properties properties = System.getProperties();
//
//        System.out.println(properties);

        System.clearProperty("org.apache.commons.logging.Log");

        CommonLog2 commonLog2 = new CommonLog2();

        Log logger = LogFactory.getLog(CommonLogTest.class);
        logger.info("It is just commons logging test before closing.");
//        Properties properties2 = System.getProperties();
//        System.out.println(properties2);

    }
}
