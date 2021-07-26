package arrraylength;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayListTest {
    public static void main(String[] args) {
        Logger LOGGER = LoggerFactory.getLogger(ArrayListTest.class);
//        List<String>  str = new ArrayList<>();
//
//        boolean empty = str.isEmpty();
//        LOGGER.info("whether list is null:{}", empty);
//        for (String s : str) {
//            LOGGER.info("list is {}", s);
//        }

//        String s = null;
//        assert s != null;
//        boolean empty = s.isEmpty();
//        LOGGER.info(" null : {}", empty);

        Map<String, String> map = new HashMap();

        map.put("key1", "value1");
        map.put("key2", "");

        JSONObject productJson = new JSONObject();
        productJson.put("key3", map.get("key2"));
        LOGGER.info("value1 is {}", map.get("key1"));
        LOGGER.info("value2 is {}", map.get("key2"));

    }
}
