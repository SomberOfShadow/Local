package map;

import java.util.HashMap;

public class HashMapTest {
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("key1", "value1");
        hashMap.put("key2", "value2");

        System.out.println(hashMap.get("key1"));
        System.out.println(hashMap.get("key1"));
    }
}
