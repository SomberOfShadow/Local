package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashSet;;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Classname JavaHashSetToJson
 * @Description TODO
 * @Date 2021-07-30 5:03 PM
 * @Created by eenheni
 */
public class JavaObjectListToJson2 {

    // use CopyOnWriteArrayList, otherwise Java ConcurrentModificationException if using ArrayList
    private static CopyOnWriteArrayList<Example> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    private static HashSet<String> hashSet = new HashSet<>();
    public static void main(String[] args) {
        Example example1 = new Example("G2RbsResource1", 1);
        Example example2 = new Example("G2RbsResource2", 2);
        Example example3 = new Example("G2RbsResource3", 3);
        Example example4 = new Example("G2RbsResource3", 4);
        addIfNotExist(example1);
        addIfNotExist(example2);
        addIfNotExist(example3);
        addIfNotExist(example4);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String s = prettyGson.toJson(copyOnWriteArrayList);
        System.out.println(s);
        System.out.println("===========================");

    }


    private static void addIfNotExist(Example name3) {
        if(!hashSet.contains(name3.getName())){
            hashSet.add(name3.getName());
            copyOnWriteArrayList.add(name3);
        }
    }

}
