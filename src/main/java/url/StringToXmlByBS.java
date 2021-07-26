package url;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


/**
 *  Convert string to xml by Beautiful Soup
 *  Example data on html page:
 *  url: https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/1.8.9192/
 *  content:
 *  This XML file does not appear to have any style information associated with it. The document tree is shown below.
 * <content>
 * <data>
 * <content-item>
 * <resourceURI>https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/1.8.9192/msran-jcat-extension-with-dependencies-1.8.9192.jar</resourceURI>
 * <relativePath>/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/1.8.9192/msran-jcat-extension-with-dependencies-1.8.9192.jar</relativePath>
 * <text>msran-jcat-extension-with-dependencies-1.8.9192.jar</text>
 * <leaf>true</leaf>
 * <lastModified>2020-11-16 01:50:07.0 UTC</lastModified>
 * <sizeOnDisk>300867122</sizeOnDisk>
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * <content-item>
 * ...
 * </content-item>
 * </data>
 * </content>
 *
 *
 *
 */
public class StringToXmlByBS {
    private static final Logger LOGGER = LoggerFactory.getLogger("StringToXmlByBS.class");
    private static Elements textList;
    private static Elements sizeOnDiskList;
    private static Elements lastModifiedList;
    private static HashMap<String, Object> map = new HashMap<>();

    private static final String OLD_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";
    private static final String NEW_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";

    private static final String INDEX_NAME = "send-mje-version-size-test2";
    // mje versions between 4038 and 9142 are all old url except 9132/9133/9134/9135
//    private static final int MJE_VERSION_START = 4038;
////    private static final int MJE_VERSION_END = 9142;
//    private static final int MJE_VERSION_END = 5039;


    // 4229 does not exist
    private static final int MJE_VERSION_START = 4229;
    private static final int MJE_VERSION_END = 4239;

    private static String MJE_VERSION_PREFIX = "1.8.";

//    private static int MJE_VERSION_SUFFIX = 4229; // just for a single test

    private static String MJE_VERSION = "";

    private static RestHighLevelClient client = null;

    private static final String REGEX = "msran-jcat-extension-with-dependencies-1.8.\\d\\d\\d\\d.jar";

    public static void main(String[] args) {

        // Collect all specified mje version data
        for (int i = MJE_VERSION_START; i <= MJE_VERSION_END; i++) {
            clear();
            MJE_VERSION = MJE_VERSION_PREFIX + i;
            getDataFromUrl(i);
            int position = matchJarAndRecord();

            HashMap<String, Object> mapData = getMapData(position);
            sendDataToElasticsearch(mapData);

            LOGGER.info("------------------------------------");
        }
        close();

        // Collect a specific mje version data
//        getDataFromUrl(MJE_VERSION_SUFFIX);
//        int position = matchJarAndRecord();
//
//        HashMap<String, Object> mapData = getMapData(position);
//        sendDataToElasticsearch(mapData);
//
//        clear();
//        close();

    }


    /**
     *  Match Jar and record position
     *
     */
    private static int matchJarAndRecord() {

        int position = 0;
        if (textList !=null && !textList.isEmpty()) {
            for (Element elements : textList) {
                if (elements.text().matches(REGEX)) {
                    break;
                }
                position++;
            }
            LOGGER.info("position:{}", position);
        }

        return position;
    }
    /**
     *  parse url to xml by Besautiful Soup and get target data
     *
     */
    private static void getDataFromUrl(int mjeVersionSuffix) {

        String mjeVersionOldUrl = OLD_REPO_URL + MJE_VERSION_PREFIX + mjeVersionSuffix ;
        String mjeVersionNewUrl = NEW_REPO_URL + MJE_VERSION_PREFIX + mjeVersionSuffix;

        Document doc = null;
        try {
            doc = Jsoup.connect(mjeVersionOldUrl).get();
            LOGGER.info("Succeed to get data from old url for mje version {}!", MJE_VERSION);
        } catch (Exception e) {
            LOGGER.warn("Fail to get data from old url! Try to get from new url ......");
            try {
                doc = Jsoup.connect(mjeVersionNewUrl).get();
                LOGGER.info("Succeed to get data from new url for mje version {}!", MJE_VERSION);
            } catch (Exception ex) {
               LOGGER.warn("Fail to get data from new url for mje version {} too!", MJE_VERSION, e);
            }
        }

        if (doc !=null ) {
            textList = doc.getElementsByTag("text");
            sizeOnDiskList = doc.getElementsByTag("sizeOnDisk");
            lastModifiedList = doc.getElementsByTag("lastModified");
        }
    }


    /**
     *  Put data in HashMap
     *
     * @param flag position mark
     * @Return HashMap<String, Object>

     */
    private static HashMap<String, Object> getMapData(int flag) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (textList !=null && !textList.isEmpty()) {
            map.put("mjeVersion", MJE_VERSION);
            String sizeOnDisk = sizeOnDiskList.get(flag).text();
            String lastModified = lastModifiedList.get(flag).text();
            map.put("sizeOnDisk", Integer.parseInt(sizeOnDisk));
            map.put("lastModified", lastModified.substring(0,19));

//            String dateString = lastModified.substring(0,19);
//
//            try {
//                date = dateFormat.parse(dateString );
//            } catch (ParseException e) {
//                LOGGER.warn("Fail to convert date {} to unix time!", dateString);
//            }
//            long unixTime = (long) date.getTime()/1000;
//            map.put("lastModified", unixTime);

        }
        else { // if mje version does not exist, set sizeOnDisk to be 0 and lastModified to be empty string
            map.put("mjeVersion", MJE_VERSION);
            map.put("sizeOnDisk", 0);
            map.put("lastModified", "1970-01-01 00:00:00");

//            String s = "1970-01-01 00:00:00";
//            try {
//                Date de = dateFormat.parse(s);
//                map.put("lastModified", de.getTime()/1000);
//            } catch (ParseException e) {
//                LOGGER.warn("Fail to convert date {} to unix time!", s);
//            }

        }

        return map;
    }


    /**
     *  Send data to Elasticsearch
     *
     * @param hashMap HashMap object
     */
    public static void sendDataToElasticsearch(HashMap hashMap){

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("esuser", "Z8A6v9xG1oS3ufU"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("mje-es.sero.wh.rnd.internal.ericsson.com", 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);

        IndexRequest request = new IndexRequest(INDEX_NAME).id(MJE_VERSION);


        request.source(hashMap, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("Succeed to send data to Elasticsearch for mje version {}!", MJE_VERSION);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to send data to Elasticsearch for mje version {}!", MJE_VERSION, e);
        }
    }

    /**
     *  clear current list element to collect next mje version data
     *
     */
    private static void clear() {
        if (textList != null) {
            textList.clear();
        }
        if (sizeOnDiskList !=null) {
            sizeOnDiskList.clear();
        }
        if (lastModifiedList !=null) {
            lastModifiedList.clear();
        }
        if (map != null) {
            map.clear();
        }
    }

    /**
     *  close Rest client finally
     *
     */
    private static void close() {
        try {
            client.close();
        } catch (Exception e) {
            LOGGER.warn("Fail to close client!");
        }
    }

}
