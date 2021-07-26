package elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CollectMjeVersionData {
    private static final Logger LOGGER = LoggerFactory.getLogger("CollectMjeVersionData.class");
    private static Elements textList;
    private static Elements sizeOnDiskList;
    private static Elements lastModifiedList;
    private static HashMap<String, Object> map = new HashMap<>();

    private static final String OLD_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";
    private static final String NEW_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";

//    private static final String INDEX_NAME = "mapping-test";
    private static final String INDEX_NAME = "reduce-lucene-test";
    // mje versions between 4038 and 9142 are all old url except 9132/9133/9134/9135
    // 4229 does not exist
//    private static final int MJE_VERSION_START = 4239;
//    private static final int MJE_VERSION_END = 4249;
//
//    private static String MJE_VERSION_PREFIX = "1.8.";

    private static String MJE_VERSION_PREFIX = "1.9.";
    private static final int MJE_VERSION_START = 349;
    private static final int MJE_VERSION_END = 349;



    private static String MJE_VERSION = "";


    private static final String REGEX = "msran-jcat-extension-with-dependencies-1.\\d.\\d*.jar";

    private  static final String MAPPINGS = "{\n" +
            " \"properties\" : {\n" +
            "    \"lastModified\" : {\n" +
            "      \"type\" : \"date\",\n" +
            "\t  \"format\" : \"yyyy-MM-dd HH:mm:ss\"\n" +
            "    },\n" +
            "    \"mjeVersion\" : {\n" +
            "      \"type\" : \"text\",\n" +
            "      \"fields\" : {\n" +
            "        \"keyword\" : {\n" +
            "          \"type\" : \"keyword\",\n" +
            "          \"ignore_above\" : 256\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    \"sizeOnDisk\" : {\n" +
            "      \"type\" : \"long\"\n" +
            "    }\n" +
            "  }\n" +
            "}";


    public static void main(String[] args) {

        RestHighLevelClient client = ElasticsearchUtils.getClient();

        if (!ElasticsearchUtils.existIndex(client, INDEX_NAME)) {
            if (ElasticsearchUtils.createIndex(client, INDEX_NAME)) {
                ElasticsearchUtils.putMappings(client, MAPPINGS, INDEX_NAME);
            }
        }

        // Collect all specified mje version data
        for (int i = MJE_VERSION_START; i <= MJE_VERSION_END; i++) {
            clear();
            MJE_VERSION = MJE_VERSION_PREFIX + i;
            getDataFromUrl(i);
            int position = matchJarAndRecord();

            HashMap<String, Object> mapData = getMapData(position);

            //send data to elasticsearch
            ElasticsearchUtils.sendDataToElasticsearch(client, mapData, MJE_VERSION, INDEX_NAME);

            LOGGER.info("------------------------------------");
        }

       ElasticsearchUtils.close(client);

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

        if (textList !=null && !textList.isEmpty()) {
            map.put("mjeVersion", MJE_VERSION);
            String sizeOnDisk = sizeOnDiskList.get(flag).text();
            String lastModified = lastModifiedList.get(flag).text();
            map.put("sizeOnDisk", Integer.parseInt(sizeOnDisk));
            map.put("lastModified", lastModified.substring(0,19));

        } else { // if mje version does not exist, set sizeOnDisk to be 0 and lastModified to be empty string
            map.put("mjeVersion", MJE_VERSION);
            map.put("sizeOnDisk", 0);
            map.put("lastModified", "1970-01-01 00:00:00");
        }
        return map;
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


}
