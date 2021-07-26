package json.jsonarray;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import stpdata.hardware.HardwareData;

import java.util.ArrayList;
import java.util.List;

public class JsonArrayTest {
    public static final Logger LOGGER = LoggerFactory.getLogger("JsonArrayTest.class");

    public static void main(String[] args) {

        String str = "[{\"serialNumber\":\"D16L427157\",\"productionDate\":\"2014-12-19T00:00:00+00:00\",\"linkHandlerAddress\":\"000100\",\"productNumber\":\"KDU137925/3\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=1\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R5A\",\"productName\":\"DUS 52 01\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"baseband\"},\n" +
                "{\"serialNumber\":\"CF81877876\",\"productionDate\":\"2014-05-13T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2051\",\"productNumber\":\"KRC 161 349/2\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=7\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R1B\",\"productName\":\"RRUS 12 B4\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"D821628368\",\"productionDate\":\"2015-08-18T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2048\",\"productNumber\":\"KRC 161 282/3\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=2\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R1B\",\"productName\":\"RRUS 12 B3\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"CC47171619\",\"productionDate\":\"2011-10-04T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2053\",\"productNumber\":\"KRC 161 255/1\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=9\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R1F\",\"productName\":\"RRUS11\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"D16E588612\",\"productionDate\":\"2014-05-12T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2049\",\"productNumber\":\"KRC 118 59/2\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=3\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R2D\",\"productName\":\"RUS 01 B4\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"D16E588621\",\"productionDate\":\"2014-05-12T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2050\",\"productNumber\":\"KRC 118 59/2\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=5\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R2D\",\"productName\":\"RUS 01 B4\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"D16E588596\",\"productionDate\":\"2014-05-12T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2054\",\"productNumber\":\"KRC 118 59/2\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=4\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R2D\",\"productName\":\"RUS 01 B4\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"},\n" +
                "{\"serialNumber\":\"CF81852117\",\"productionDate\":\"2014-04-16T00:00:00+00:00\",\"linkHandlerAddress\":\"BXP_2052\",\"productNumber\":\"KRC 161 349/2\",\"localDistinguishedName\":\"ManagedElement=1,Equipment=1,FieldReplaceableUnit=8\",\"nodeId\":\"g2rbs1\",\"productRevision\":\"R1B\",\"productName\":\"RRUS 12 B4\",\"productType\":\"FieldReplaceableUnit\",\"logicalProductType\":\"radio\"}]";

        JSONArray jsonArray = new JSONArray(str);

//        JSONArray jsonArray = new JSONArray();

//        JSONObject jsonObject1 = new JSONObject();
//        JSONObject jsonObject2 = new JSONObject();
//
//        jsonObject1.put("nodeId", "node1");
//        jsonObject1.put("date", "202008201");
//        jsonObject1.put("productionDate", "2015-10-26T00:00:00+00:00");
//
//        jsonObject2.put("nodeId", "node2");
//        jsonObject2.put("date", "202008202");
//        jsonObject2.put("productionDate", "2016-10-26T00:00:00+00:00");
//
//        jsonArray.put(jsonObject1);
//        jsonArray.put(jsonObject2);

        List<HardWareTest> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            HardWareTest hardWareTest = new HardWareTest();

            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            HardWareTest hardWareTest1 = new Gson().fromJson(jsonObject.toString(), HardWareTest.class);

            HardwareData hardwareData = new Gson().fromJson(jsonObject.toString(), HardwareData.class);

            LOGGER.info("HardwareData is {}", hardwareData.toString());
//            LOGGER.info("node id is {}", hardWareTest1.getNodeId());
//
//            LOGGER.info("date is {}", hardWareTest1.getDate());
//
//            LOGGER.info("production date is {}", hardwareData.getProductionDate());
//            LOGGER.info("linkHandler is {}", hardwareData.getLinkHandler());

//            list.add(hardWareTest);
        }

        //json object to list seems not correct
        List<Object> objects = jsonArray.toList();


//        for (Object o : objects) {
//
//            HardWareTest hardWareTest = new HardWareTest();
//            HardWareTest hardWareTest1 = new Gson().fromJson(o.toString(), HardWareTest.class);
//            hardWareTest.setNodeId(hardWareTest1.getNodeId());
//            hardWareTest.setDate(hardWareTest1.getDate());
//
//            LOGGER.info("node id is {}", hardWareTest1.getNodeId());
//
//            LOGGER.info("date is {}", hardWareTest1.getDate());
//
//            LOGGER.info("production date is {}", hardWareTest1.getProductionDate());
//            list.add(hardWareTest);
//        }


    }
}
