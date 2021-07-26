package elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.DocWriteResponse;
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
import java.util.concurrent.TimeUnit;

/**
 *  @ClassName: ElasticsearchUtils
 *  @Description: Define Elasticsearch relative method
 *
 */
public class ElasticsearchUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("ElasticsearchUtils.class");


    public static RestHighLevelClient getClient(){
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("esuser", "Z8A6v9xG1oS3ufU"));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost("mje-es.sero.wh.rnd.internal.ericsson.com", 443, "https"))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        RestHighLevelClient client = new RestHighLevelClient(builder);
        return client;
    }

    /**
     *  Send data to Elasticsearch
     *
     * @param hashMap HashMap object
     */
    public static void sendDataToElasticsearch(RestHighLevelClient client, HashMap hashMap, String id, String indexName){

        IndexRequest request = new IndexRequest(indexName).id(id);

        request.source(hashMap, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            if (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("Succeed to send data to Elasticsearch for mje version {}!", id);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to send data to Elasticsearch for mje version {}!", id, e);
        }
    }

    /**
     *  Create a specific index in Elasticsearch
     *
     */
    public static boolean createIndex(RestHighLevelClient client, String indexName){

        boolean create = false;
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            create = createIndexResponse.isAcknowledged();
            if (create) {
                LOGGER.info("Succeed to create index {}!", indexName);
            } else {
                LOGGER.warn("Fail to create index {}!", indexName);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to create index {}!", indexName, e);
        }
        return create;
    }


    /**
     *  Check index whether exists in Elasticsearch
     *
     */
    public static boolean existIndex(RestHighLevelClient client, String indexName){

        boolean exists = false;
        GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
        try {
            exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
                LOGGER.info("Index {} already exists!", indexName);
            } else {
                LOGGER.warn("Index {} does not exist!", indexName);
            }

        } catch (Exception e) {
            LOGGER.warn("Fail to check index {} whether exists!", indexName, e);
        }
        return exists;
    }

    /**
     *  Put mapping for a index in Elasticsearch
     *
     */
    public static boolean putMappings(RestHighLevelClient client, String mappings, String indexName){
        boolean putMappings =  false;

        try {
            PutMappingRequest putMappingRequest = new PutMappingRequest(indexName);
            putMappingRequest.source(mappings,  XContentType.JSON);
            AcknowledgedResponse putMappingResponse = client.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);

            putMappings = putMappingResponse.isAcknowledged();
            if (putMappings) {
                LOGGER.info("Succeed to put mappings for index: {}!", indexName);
            } else {
                LOGGER.info("Fail to put mappings for index: {}!", indexName);
            }

        } catch (Exception e) {
            LOGGER.info("Fail to put mappings for index:{} due to Exception:\n", indexName, e);
        }
        return putMappings;
    }


    /**
     *  close Rest client finally
     *
     */
    public static void close(RestHighLevelClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (Exception e) {
                LOGGER.warn("Fail to close client!");
            }
        }


    }
}
