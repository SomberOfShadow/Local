package json.jsonarray;


import com.google.gson.JsonObject;

public class JsonNullTest {
    public static void main(String[] args) {

        JsonObject jsonObject1 = new JsonObject();

//        jsonObject1.addProperty("nodeId", );
        jsonObject1.add("nodeId", null);
        jsonObject1.addProperty("date", "202008201");
        jsonObject1.addProperty("productionDate", "2015-10-26T00:00:00+00:00");

        boolean isNodeIdNull = jsonObject1.get("nodeId").isJsonNull();
        System.out.println(isNodeIdNull);

    }
}
