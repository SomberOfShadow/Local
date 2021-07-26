package directory.size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DecimalFormat;

public class LogSize {
    public static void main(String[] args) {

        Logger LOGGER = LoggerFactory.getLogger("LogSize");

        String dir = "C:\\MySoftWare\\Java\\workspace\\system_and_name";
        long lengthLong = getLengthLong(dir);
        LOGGER.error("directory length is {} ", lengthLong);

        String lengthStr = getLengthStr(dir);
        LOGGER.error("directory size is {}", lengthStr);

    }




    /**
     *
     * Get file / folder size, return bytes
     * @param directory
     * @return size
     */
    public static long getLengthLong(String directory) {
        long totalLength = 0;

        File file = new File(directory);
        if (file.isFile()) {
            totalLength += file.length();
        } else {
            File[] files = file.listFiles();
            if (files != null) {
                for (File childFile : files) {
                    totalLength += getLengthLong(childFile.getPath());
                }
            }
        }

        return totalLength;
    }

    /**
     * Get file size, return string, with unit, such as 1 MB
     * @param directory
     * @return size
     */
    public static String getLengthStr(String directory) {
        float lengthLong = getLengthLong(directory);

        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        long tb = gb * 1024;

        String unit;
        float ret;
        DecimalFormat format = new DecimalFormat(".00");
        if (lengthLong < kb) {
            unit = "B";
            ret = lengthLong;
        } else if (lengthLong < mb) {
            unit = "KB";
            ret = lengthLong / kb;
        } else if (lengthLong < gb) {
            unit = "MB";
            ret = lengthLong / mb;
        } else if (lengthLong < tb) {
            unit = "GB";
            ret = lengthLong / gb;
        } else {
            unit = "TB";
            ret = lengthLong / tb;
        }

        return format.format(ret) + " " + unit;
    }

}
