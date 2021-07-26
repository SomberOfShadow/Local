package url;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 *  @Description: To get content from a url and transfer from string to XML , then filter the data we want
 *                Next step is to send data to Elasticsearch by SCF
 *
 *  @ClassName: StringToXmlExample
 */
public class StringToXmlExample {

    public static void main(String[] args) throws Exception {


        String mjeVersionUrl = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/1.8.9092/";
//
        String xmlStr = getData(mjeVersionUrl);

        //Use method to convert XML string content to XML Document object
        Document doc = convertStringToXMLDocument(xmlStr);

        //Verify XML document is build correctly
        System.out.println(doc.getFirstChild().getNodeName());

        NodeList elementsByTagName = doc.getElementsByTagName("content-item");
        System.out.println(elementsByTagName.getLength());
        int length = elementsByTagName.item(0).getChildNodes().getLength();
        System.out.println(length);

//        NodeList childNodes = elementsByTagName.item(0).getChildNodes();
//        for (int i =0 ; i < childNodes.getLength(); i++) {
//
//            System.out.println(childNodes.item(i).getNodeName() + "--" + childNodes.item(i).getTextContent());

//            Object sizeOnDisk = childNodes.item(i).getUserData("sizeOnDisk");
//            System.out.println(sizeOnDisk);
//        }

        final String regex = "msran-jcat-extension-with-dependencies-1.8.\\d\\d\\d\\d.jar";


        int flag = -1;
        for (int j = 0; j < elementsByTagName.getLength(); j++) {
            NodeList childNodes = elementsByTagName.item(j).getChildNodes();

            for (int i =0 ; i < childNodes.getLength(); i++) {
                if (childNodes.item(i).getNodeName().equals("text")
                        && childNodes.item(i).getTextContent().matches(regex)) {
                    flag = j;
                    System.out.println("flag is :" + flag);
                    break;
                }
            }
            // to get size on disk
           if (flag != -1) {
               NodeList nodeList = elementsByTagName.item(flag).getChildNodes();
               for (int k =0 ; k < nodeList.getLength(); k++) {
                   if (nodeList.item(k).getNodeName().equals("sizeOnDisk")) {
                       System.out.println("size on disk is :" + nodeList.item(k).getTextContent());
                   }
               }
               break;
           }

        }

        //print a xml document
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        //initialize StreamResult with File object to save to file
        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);
        String xmlString = result.getWriter().toString();
//        System.out.println(xmlString);

    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        Document doc = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            doc = builder.parse(new InputSource(new StringReader(xmlString)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
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
