package url;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.client.indices.PutMappingRequest;
// deprecated class
//import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ElaticsearchIncludeNameTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("ElaticsearchIncludeNameTest.class");

    private static Elements textList;
    private static Elements sizeOnDiskList;
    private static Elements lastModifiedList;
    private static HashMap<String, Object> mjeinfo = new HashMap<>();

    private static final String OLD_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/jcat-releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";
    private static final String NEW_REPO_URL = "https://arm2s10-eiffel026.eiffel.gic.ericsson.se:8443/nexus/service/local/repositories/mje_releases/content/com/ericsson/msran/jcat/msran-jcat-extension-with-dependencies/";

    private static final String INDEX_NAME = "test-date";
    // mje versions between 4038 and 9142 are all old url except 9132/9133/9134/9135
    private static final int MJE_VERSION_START = 4038;
//    private static final int MJE_VERSION_END = 9142;
    private static final int MJE_VERSION_END = 4048;


    private static String MJE_VERSION_PREFIX = "1.8.";

    private static String MJE_VERSION = "";

    private static RestHighLevelClient client = null;

    private static final String REGEX = "msran-jcat-extension-with-dependencies-1.8.\\d\\d\\d\\d.jar";

//    private static final String TYPE = "_doc";
    private static Map<String, Object> mapping = new HashMap<>();

//    private static final String MAPPINGS_WITH_TYPE = "{\n" +
//            "    \"_doc\": {          \n" +
//            "      \"properties\": {\n" +
//            "        \"lastModified\": { \n" +
//            "        \"type\": \"text\"\n" +
//            "        },\n" +
//            "        \"mjeVersion\": {   \n" +
//            "            \"type\": \"text\"\n" +
//            "        },\n" +
//            "       \"sizeOnDisk\": {   \n" +
//            "           \"type\": \"long\"\n" +
//            "        }\n" +
//            "      }\n" +
//            "    }\n" +
//            "}";

    private static final String MAPPINGS = "{\n" +
            "    \"properties\": {\n" +
            "      \"collectTime\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"lastModified\": {\n" +
            "        \"type\": \"date\"\n" +
            "      },\n" +
            "      \"mjeVersion\": {\n" +
            "        \"type\": \"text\",\n" +
            "        \"fields\": {\n" +
            "          \"keyword\": {\n" +
            "            \"type\": \"keyword\",\n" +
            "            \"ignore_above\": 256\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"sizeOnDisk\": {\n" +
            "        \"type\": \"long\"\n" +
            "      }\n" +
            "    }\n" +
            "}";


    public static void main(String[] args) {

//        createMapping();

        client = getClient();
//        if (!existIndex(client)) {
//            createIndex(client);
//        }


        if (!existIndex(client)) {
            if (createIndex(client)) {
                putMappings(client);
            }
        }

        // Collect all specified mje version data and insert  into index as document
        for (int i = MJE_VERSION_START; i <= MJE_VERSION_END; i++) {
            clear();
            MJE_VERSION = MJE_VERSION_PREFIX + i;
            getDataFromUrl(i);
            int position = matchJarAndRecord();

            HashMap<String, Object> mapData = getMapData(position);
            sendDataToElasticsearch(client, mapData);

            LOGGER.info("------------------------------------");
        }

        close();

    }

    /**
     *  Create mapping for mje version data
     *
     */
    private static void createMapping (){
        Map<String, Object> mjeVersion = new HashMap<>();
        mjeVersion.put("type", "text");

        Map<String, Object> sizeOnDisk = new HashMap<>();
        sizeOnDisk.put("type", "long");

        Map<String, Object> lastModified = new HashMap<>();
        lastModified.put("type", "date");

        Map<String, Object> collectTime = new HashMap<>();
        collectTime.put("type", "date");

        Map<String, Object> properties = new HashMap<>();
        properties.put("mjeVersion", mjeVersion);
        properties.put("sizeOnDisk", sizeOnDisk);
        properties.put("lastModified", lastModified);
        properties.put("collectTime", collectTime);

        mapping.put("properties", properties);
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
        mjeinfo.put("mjeVersion", MJE_VERSION);
        if (textList !=null && !textList.isEmpty()) {
            String sizeOnDisk = sizeOnDiskList.get(flag).text();
            String lastModified = lastModifiedList.get(flag).text();
            mjeinfo.put("sizeOnDisk", Integer.parseInt(sizeOnDisk));
            mjeinfo.put("lastModified", lastModified.split(" ")[0]);
            mjeinfo.put("collectTime", System.currentTimeMillis());
        } else { // if mje version does not exist, set sizeOnDisk to be 0 and lastModified to be empty string
            mjeinfo.put("sizeOnDisk", 0);
            mjeinfo.put("lastModified", "");
            mjeinfo.put("collectTime", System.currentTimeMillis());
        }

        return mjeinfo;
    }


    private static RestHighLevelClient getClient(){

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("esuser", "Z8A6v9xG1oS3ufU"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("mje-es.sero.wh.rnd.internal.ericsson.com", 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);
        return client;
    }

    /**
     *  Send data to Elasticsearch
     *
     * @param hashMap HashMap object
     */
    private static void sendDataToElasticsearch(RestHighLevelClient client, HashMap hashMap){

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
     *  Create a specific index in Elasticsearch
     *
     */
    private static boolean createIndex(RestHighLevelClient client){

        boolean create = false;
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX_NAME);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            create = createIndexResponse.isAcknowledged();
            if (create) {
                LOGGER.info("Succeed to create index {}!", INDEX_NAME);
            } else {
                LOGGER.warn("Fail to create index {}!", INDEX_NAME);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to create index {}!", INDEX_NAME, e);
        }
        return create;
    }


    /**
     *  Check index whether exists in Elasticsearch
     *
     */
    private static boolean existIndex(RestHighLevelClient client){

        boolean exists = false;
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);

        try {
            exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
                LOGGER.info("Index {} already exists!", INDEX_NAME);
            } else {
                LOGGER.warn("Index {} does not exist!", INDEX_NAME);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to check index {} whether exists!", INDEX_NAME, e);
        }
        return exists;
    }

    /**
     *  Put mapping for a index in Elasticsearch
     *
     */
    private static boolean putMappings(RestHighLevelClient client){
        boolean putMappings =  false;

        try {
            // use type to specific "_type" in document, default value is "_doc"
            PutMappingRequest putMappingRequest = new PutMappingRequest(INDEX_NAME);
//            putMappingRequest.source(mapping);

            putMappingRequest.source(MAPPINGS, XContentType.JSON);

            //deprecated
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);

            // use this new API will cause an exception:
            /**
             *
             * org.elasticsearch.ElasticsearchStatusException: Elasticsearch exception [type=illegal_argument_exception, reason=Types cannot be provided in put mapping requests, unless the include_type_name parameter is set to true.]
             * 	at org.elasticsearch.rest.BytesRestResponse.errorFromXContent(BytesRestResponse.java:177) ~[elasticsearch-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.RestHighLevelClient.parseEntity(RestHighLevelClient.java:1727) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.RestHighLevelClient.parseResponseException(RestHighLevelClient.java:1704) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.RestHighLevelClient.internalPerformRequest(RestHighLevelClient.java:1467) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.RestHighLevelClient.performRequest(RestHighLevelClient.java:1439) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.RestHighLevelClient.performRequestAndParseEntity(RestHighLevelClient.java:1406) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at org.elasticsearch.client.IndicesClient.putMapping(IndicesClient.java:199) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 	at url.ElaticsearchIncludeNameTest.putMappings(ElaticsearchIncludeNameTest.java:307) [classes/:?]
             * 	at url.ElaticsearchIncludeNameTest.main(ElaticsearchIncludeNameTest.java:97) [classes/:?]
             * 	Suppressed: org.elasticsearch.client.ResponseException: method [PUT], host [https://mje-es.sero.wh.rnd.internal.ericsson.com:443], URI [/send-mje-version-size-test3/_mapping?master_timeout=30s&timeout=30s], status line [HTTP/1.1 400 Bad Request]
             * {"error":{"root_cause":[{"type":"illegal_argument_exception","reason":"Types cannot be provided in put mapping requests, unless the include_type_name parameter is set to true."}],"type":"illegal_argument_exception","reason":"Types cannot be provided in put mapping requests, unless the include_type_name parameter is set to true."},"status":400}
             * 		at org.elasticsearch.client.RestClient.convertResponse(RestClient.java:253) ~[elasticsearch-rest-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.RestClient.performRequest(RestClient.java:231) ~[elasticsearch-rest-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.RestClient.performRequest(RestClient.java:205) ~[elasticsearch-rest-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.RestHighLevelClient.internalPerformRequest(RestHighLevelClient.java:1454) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.RestHighLevelClient.performRequest(RestHighLevelClient.java:1439) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.RestHighLevelClient.performRequestAndParseEntity(RestHighLevelClient.java:1406) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 		at org.elasticsearch.client.IndicesClient.putMapping(IndicesClient.java:199) ~[elasticsearch-rest-high-level-client-7.3.1.jar:7.3.1]
             * 		at url.ElaticsearchIncludeNameTest.putMappings(ElaticsearchIncludeNameTest.java:307) [classes/:?]
             * 		at url.ElaticsearchIncludeNameTest.main(ElaticsearchIncludeNameTest.java:97) [classes/:?]
             */

//            AcknowledgedResponse putMappingResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);

            putMappings = putMappingResponse.isAcknowledged();
            if (putMappings) {
                LOGGER.info("Succeed to put mappings for index: {}!", INDEX_NAME);
            } else {
                LOGGER.info("Fail to put mappings for index: {}!", INDEX_NAME);
            }

        } catch (Exception e) {
            LOGGER.info("Fail to put mappings for index:{} due to Exception:\n", INDEX_NAME, e);
        }
        return putMappings;
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
        if (mjeinfo != null) {
            mjeinfo.clear();
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
