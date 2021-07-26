package url;


import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class GetXmlFromUrl {
    public static void main(String[] args) throws Exception {

//        String mjeVersionsUrl = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";

        String mjeVersionUrl = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/1.8.9092/";


        String xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\">\n";
        String data = getDataFromUrl(mjeVersionUrl);
        String xmlData = xmlHeader + data;
//        Document document = stringToXml(xmlData);
        System.out.println(xmlData);

    }

    private static Document stringToXml(String str) throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        InputStream is = new ByteArrayInputStream(str.getBytes("utf-8"));
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = dbf.newDocumentBuilder().parse(is);

        is.close();
        return doc;
    }

    private static String getDataFromUrl(String url) {
        InputStream is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new URL(url).openConnection().getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }



    // read data by line from url
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
