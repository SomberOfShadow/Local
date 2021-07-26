package mysql;

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
public class SendMjeDataToMysql {
    private static final Logger LOGGER = LoggerFactory.getLogger("SendMjeDataToMysql.class");
    private static Elements textList;
    private static Elements sizeOnDiskList;
    private static Elements lastModifiedList;
    private static HashMap<String, Object> map = new HashMap<>();

    private static final String OLD_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";
    private static final String NEW_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";
    // mje versions between 4038 and 9142 are all old url except 9132/9133/9134/9135
    // 4229 does not exist
    private static final int MJE_VERSION_START = 4229;
    private static final int MJE_VERSION_END = 4249;

    private static String MJE_VERSION_PREFIX = "1.8.";

    private static String MJE_VERSION = "";

    private static MysqlUtil mysqlUtil = new MysqlUtil();
    private static final String REGEX = "msran-jcat-extension-with-dependencies-1.8.\\d\\d\\d\\d.jar";

    public static void main(String[] args) {

        mysqlUtil = new MysqlUtil();
        mysqlUtil.getConnection();
//        mysqlUtil.setTableName("mje_version_info");
        mysqlUtil.setTableName("mje_datetime_version_info");

        if (!mysqlUtil.exist()) {
            mysqlUtil.createTable();
        }

        // Collect all specified mje version data
        for (int i = MJE_VERSION_START; i <= MJE_VERSION_END; i++) {
            MJE_VERSION = MJE_VERSION_PREFIX + i;
            getDataFromUrl(i);
            int position = matchJarAndRecord();
            getAndInsert(position);
            LOGGER.info("------------------------------------");
        }

        mysqlUtil.close();
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


    private static void getAndInsert(int flag) {

        if (textList !=null && !textList.isEmpty()) {
            String sizeOnDisk = sizeOnDiskList.get(flag).text();
            String lastModified = lastModifiedList.get(flag).text();

            mysqlUtil.insert(MJE_VERSION, Integer.parseInt(sizeOnDisk), lastModified.substring(0, 19));

        }
//        else { // if mje version does not exist, set sizeOnDisk to be 0 and lastModified to be empty string
//            mysqlUtil.insert(MJE_VERSION, 0, "");
//        }

    }

}
