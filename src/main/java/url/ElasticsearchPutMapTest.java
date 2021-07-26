package url;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteResponse;
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
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ElasticsearchPutMapTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("ElasticsearchPutMapTest.class");
    private static final String INDEX_NAME = "mapping-test";

    private static String MJE_VERSION = "";

    private static RestHighLevelClient client = null;

    private static final String TYPE = "doc";
    private static Map<String, Object> mapping = new HashMap<>();

//    private static final String MAPPINGS =
//            "{\n" +
//                    "        \"properties\" : {\n" +
//                    "            \"lastModified\" : { \"type\" : \"text\" },\n" +
//                    "\t\t\t      \"mjeVersion\" : { \"type\" : \"text\" },\n" +
//                    "\t\t\t      \"sizeOnDisk\" : { \"type\" : \"long\" }\n" +
//                    "        }\n" +
//                    "}";


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

//        createMapping();
        client = getClient();
        if (!existIndex(client)) {
            if (createIndex(client)) {
                putMappings(client);
            }
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
        sizeOnDisk.put("type", "text");

        Map<String, Object> lastModified = new HashMap<>();
        lastModified.put("type", "text");

        Map<String, Object> properties = new HashMap<>();
        properties.put("mjeVersion", mjeVersion);
        properties.put("sizeOnDisk", sizeOnDisk);
        properties.put("lastModified", lastModified);

        mapping.put("properties", properties);
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
            // use type to specific "type" in document, default value is "doc" after 7.0
//        PutMappingRequest putMappingRequest = new PutMappingRequest(INDEX_NAME).type(TYPE);

//        putMappingRequest.source(mapping);
            PutMappingRequest putMappingRequest = new PutMappingRequest(INDEX_NAME);


            putMappingRequest.source(MAPPINGS,  XContentType.JSON);
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);

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
