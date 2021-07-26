package curl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CurlTest {
    public static void main(String[] args) throws IOException {

        String[] curls = {"curl", "-XPOST", "http://localhost:9208/mje-scf-v2-20200319-1/_doc/91hV8XABSlP0aaay5suB/_update", "-H","Content-Type: application/json", "-d",     "  {\n" +
                "            \"doc\" : {\n" +
                "            \"mje-data-sending-duration\" : 5000\n" +
                "        }\n" +
                "        }" };

//        String[] cmds = {"curl", "-v", "https://api.sandbox.paypal.com/v1/oauth2/token", "-H", "Accept:application/json", "-H", "Accept-Language:en_US", "-u", "AZcQqgjuWGDk55NQ0h1C878c21SxLhUUIBQVV2ZRd5bf9PCgBI2rrCMHeFdStI9IDIEkYoUDpCpFHV79:ECLmfhZgXlDwmwjqMOJz4oDLdg_BIcCZKrpdwCXQKZaEgf0-0MYP7iTFpOJBobMFQLa2L1UWRM1cV2kQ", "-d", "grant_type=client_credentials"};
        ProcessBuilder pb = new ProcessBuilder(curls);
        pb.redirectErrorStream(true);
        Process p;
        String s = "";
//        Process start = pb.start();

        try {
            p = pb.start();
            BufferedReader br = null;
            String line = null;
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = br.readLine()) != null) {
                s=line;
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




