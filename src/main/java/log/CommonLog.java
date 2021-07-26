package log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class CommonLog {

    private final static Log logger = LogFactory.getLog(CommonLog.class);

    CommonLog() {
        logger.info("It is commons logging in CommonLog.");
    }
}
