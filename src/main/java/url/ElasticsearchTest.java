package url;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ElasticsearchTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("ElasticsearchTest.class");


    private static RestHighLevelClient client = null;
    private static final String INDEX_NAME = "send-mje-version-size-test3";

    private static final String PUT_MAP = "{\n" +
            "    \"properties\": {\n" +
            "      \"lastModified\": {\n" +
            "        \"type\": \"date\",\n" +
            "      },\n" +
            "      \"mjeVersion\": {\n" +
            "        \"type\": \"text\",\n" +
            "      },\n" +
            "      \"sizeOnDisk\": {\n" +
            "        \"type\": \"long\"\n" +
            "      }\n" +
            "    }\n" +
            "}";
    public static void main(String[] args) throws IOException {


        Map<String, Object> mjeVersion = new HashMap<>();
        mjeVersion.put("type", "text");

        Map<String, Object> sizeOnDisk = new HashMap<>();
        mjeVersion.put("type", "text");

        Map<String, Object> lastModified = new HashMap<>();
        mjeVersion.put("type", "date");

        Map<String, Object> properties = new HashMap<>();
        properties.put("mjeVersion", mjeVersion);
        properties.put("sizeOnDisk", sizeOnDisk);
        properties.put("lastModified", lastModified);

        Map<String, Object> mapping = new HashMap<>();
        mapping.put("properties", properties);
//
        create(mapping);

//        getMap();

//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("lastModified", "2021-04-15");
//        hashMap.put("mjeVersion", "1.8.xxxx");
//        hashMap.put("sizeOnDisk", 1234);
//        SendMjeDataToMysql.sendDataToElasticsearch(hashMap);
    }



    private static void create(Map mapping) throws IOException {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("esuser", "Z8A6v9xG1oS3ufU"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("mje-es.sero.wh.rnd.internal.ericsson.com", 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);
        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);
        request.settings(Settings.builder()
                .put("master_timeout", TimeValue.timeValueSeconds(30))
//                .put("include_type_name", true)
        );

//        request.mapping(mapping);
//        request.mapping("", mapping);


        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = createIndexResponse.isAcknowledged();
//        String index = createIndexResponse.index();


        LOGGER.info("acknowledged:{}", acknowledged);


    }


    private static void getMap() throws IOException {

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("esuser", "Z8A6v9xG1oS3ufU"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("mje-es.sero.wh.rnd.internal.ericsson.com", 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        client = new RestHighLevelClient(builder);
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(INDEX_NAME);

        GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> mappings = getMappingResponse.mappings();
//        LOGGER.info("mappings:\n{}", mappings);

        ImmutableOpenMap<String, MappingMetaData> objectObjectCursors = mappings.get(INDEX_NAME);
//        LOGGER.info("mappings:\n{}", objectObjectCursors.toString());
        Map<String, Object> doc = objectObjectCursors.get("_doc").getSourceAsMap();
        LOGGER.info("mappings:\n{}", doc);

    }
}
