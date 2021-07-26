package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Classname JavaObjectListToJson
 * @Description Convert java bean list to json by Gson
 * @Date 2021-07-19 3:19 PM
 * @Created by eenheni
 */
public class JavaObjectListToJson {

    // use CopyOnWriteArrayList, otherwise Java ConcurrentModificationException if using ArrayList
    private static CopyOnWriteArrayList<Example> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        Example name1 = new Example("G2RbsResource1", 1);
        Example name2 = new Example("G2RbsResource2", 2);
        Example name3 = new Example("G2RbsResource3", 1);


        copyOnWriteArrayList.add(name1);
        copyOnWriteArrayList.add(name2);

        Example name4 = new Example("G2RbsResource4", 4);
        addIfNotExist(name3);
        addIfNotExist(name4);

        Iterator<Example> iterator = copyOnWriteArrayList.iterator();

        while (iterator.hasNext()){
            Example next = iterator.next();
            next.setName(next.getName().replace("Resource", ""));
        }

        Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
        String s = prettyGson.toJson(copyOnWriteArrayList);
        System.out.println(s);
        System.out.println("===========================");

    }

    private static void addIfNotExist(Example name3) {
        boolean flag = false;

        if (!copyOnWriteArrayList.isEmpty()) {
            Iterator<Example> iterator = copyOnWriteArrayList.iterator();

            while (iterator.hasNext()) {
                Example ex = iterator.next();
                if (ex.getName().equals(name3.getName())) {
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            copyOnWriteArrayList.add(name3);
        }
    }

}

class Example{
    String name;
    int id;

    public Example(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Example setName(String name) {
        this.name = name;
        return this;
    }

    public Example setId(int id) {
        this.id = id;
        return this;
    }
}