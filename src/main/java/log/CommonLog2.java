package log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommonLog2 {

    private final static Log logger = LogFactory.getLog(CommonLog.class);

    CommonLog2() {
        logger.info("It is commons logging in CommonLog2.");
    }
}
