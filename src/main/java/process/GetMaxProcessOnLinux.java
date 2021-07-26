package process;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetMaxProcessOnLinux {

private static final Logger LOGGER = LoggerFactory.getLogger("GetMaxProcessOnLinux.class");


    public static void main(String[] args) {
        String maxNumberOfProcesses = getMaxNumberOfProcesses();
        System.out.println(maxNumberOfProcesses);
    }

    private static String getMaxNumberOfProcesses() {
        if (!System.getProperty("os.name").equals("Linux")) {
            return "257839";
        }
        Pattern maxProcPattern = Pattern.compile("maxproc\\s+(\\d+)");
        String response = "";
        String command = "/bin/tcsh -c limit";
        String maxproc = "-";
        try {
            ProcessBuilder pb = new ProcessBuilder(Arrays.asList(command.split("\\s+")));
            Process proc = pb.start();
            response = IOUtils.toString(proc.getInputStream(), Charset.defaultCharset());
            System.out.println("Command response is " + response);
            proc.destroy();
//            Log.debug("LIMIT_RESPONSE: " + limitResponse);
//            for (String line : limitResponse.split(Constants.NEW_LINE)) {
            for (String line : response.split( System.getProperty("line.separator"))) {
                Matcher m = maxProcPattern.matcher(line);
                if (m.find()) {
                    maxproc = m.group(1);
                    if (Integer.parseInt(m.group(1)) < 10000) {
                        String warnMsg = "Warning, this test was run on a machine with a very low setting for thread count '"
                                + maxproc + "'. Click for more information in discourse.";
                        System.out.println(warnMsg);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.error("Could not open output from System command!", e);
        }catch ( NumberFormatException e) {
            LOGGER.debug("LIMIT_RESPONSE: FAILED", e);
        }
        return maxproc;
    }

}
