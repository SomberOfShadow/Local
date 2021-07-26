package map;

import java.util.*;

public class MapDemo {
    public static void main(String[] args) {


        List<String> documentIds = new ArrayList<>();
        documentIds.add("1");
        documentIds.add("2");

        Map<String, Object> map = new HashMap<>();
        map.put("helmChartName", "chartName");
        map.put("ExecutionEnd", "End");
        map.put("ExecutionDurationMillis", 100);
        documentIds.forEach(documentId-> map.forEach((key, value)-> {
            System.out.println("--> key: " + key + " value: " + value + " id: " + documentId);
        }));

    }
}
