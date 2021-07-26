package list;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ListAddTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ListAddTest.class);
    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();

        list1.add("list11");
        list1.add("list12");

        LOGGER.info("list1 is {}.", list1);

        list1.add("list21");
        list1.add("list22");

        list1.addAll(list2);

        LOGGER.info("after add list2 , list1 is {}", list1);
    }
}
