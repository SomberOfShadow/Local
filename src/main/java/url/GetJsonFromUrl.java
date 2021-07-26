package url;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Iterator;


public class GetJsonFromUrl {
    public static void main(String[] args) throws Exception {

        String mv = "1.8.7339-SNAPSHOT";
        String substring = mv.substring(0, 8);
//
//        System.out.println(substring);
        String product_number = "CXP9032589_8";

        //如果是black version, 那么level是0
//        String mjeVersion = "1.8.7255";
        //        String version = "R1A" + subMjeVersion;

        String mjeVersion = "1.9.328";
        String subMjeVersion = mjeVersion.substring(4);
        String version = "R1B" + subMjeVersion;
        System.out.println(subMjeVersion);
//        System.out.println("R1A" + subMjeVersion);


        String url =  "https://infobank.sero.wh.rnd.internal.ericsson.com/infobank/rest/v2/product/" + product_number + "/revision/" + version + "/confidence-level";
        System.out.println(url);


        JSONArray objects = readJsonFromUrl(url);
        getConfidentialLevel(objects);

    }

    public static int getConfidentialLevel(JSONArray objects) {
        int mjeConfidentialLevel = 0;
        for (int i = 0; i < objects.length(); i++) {

            String confidenceLevel = objects.getJSONObject(i).get("confidenceLevel").toString();
            String confidenceLevelName = new JSONObject(confidenceLevel).get("confidenceLevelName").toString();

            if (confidenceLevelName.equals("1") && mjeConfidentialLevel < 1) {
                mjeConfidentialLevel = 1;
            } else if (confidenceLevelName.equals("2") && mjeConfidentialLevel < 2) {
                mjeConfidentialLevel = 2;
            } else if (confidenceLevelName.equals("3") && mjeConfidentialLevel < 3) {
                mjeConfidentialLevel = 3;
            }
        }

        System.out.println("level is " + mjeConfidentialLevel);
        return mjeConfidentialLevel;
    }



    public static JSONArray readJsonFromUrl(String url) throws Exception {
//        InputStream is = new URL(url).openStream();

        InputStream is = new URL(url).openConnection().getInputStream();


        HttpURLConnection httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();

        httpUrlConnection.setConnectTimeout(2000);


        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
//            String jsonText = readAll(rd);

            String jsonText = getData(url);
//            System.out.println(jsonText);

            JSONArray objects = new JSONArray(jsonText);
            System.out.println(objects);
            return objects;

        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getData(String urlString) throws Exception {
        StringBuilder res = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.getMessage();
        }
        return res.toString();
    }
}

