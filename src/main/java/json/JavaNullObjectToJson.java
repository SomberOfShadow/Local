package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Classname JavaNullObjectToJson
 * @Description TODO
 * @Date 2021-08-06 10:21 AM
 * @Created by eenheni
 */
public class JavaNullObjectToJson {
    private static CopyOnWriteArrayList<Example> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        Example example = new Example();
        example.setName("");
        example.setId(1);

        Example name1 = new Example("G2RbsResource1", 1);
        Example name2 = new Example("null", 1);

        copyOnWriteArrayList.add(example);
        copyOnWriteArrayList.add(name1);
        copyOnWriteArrayList.add(name2);

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String s = prettyGson.toJson(copyOnWriteArrayList);
        System.out.println(s);
        System.out.println("===========================");

    }

}
