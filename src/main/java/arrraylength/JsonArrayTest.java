package arrraylength;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JsonArrayTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonArrayTest.class);
    private static JSONArray JSONARRAY = new JSONArray();
    public static void main(String[] args) {

        JSONArray jsonArray1 = new JSONArray();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("key1", "value1");
        jsonArray1.put(jsonObject1);
        LOGGER.info("jsonArray1 is {}", jsonArray1);

        JSONArray jsonArray2 = new JSONArray();
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("key1", "value1");
        jsonObject2.put("key3", "value3");
        jsonArray2.put(jsonObject2);
        LOGGER.info("jsonArray2 is {}", jsonArray2);

        JSONArray jsonArray = jsonArray1.put(jsonArray2);
        LOGGER.info("jsonArray is {}", jsonArray);

        JSONARRAY.put(jsonObject1).put(jsonObject2);
        LOGGER.info("JSONARRAY is {}", JSONARRAY);

    }
}
