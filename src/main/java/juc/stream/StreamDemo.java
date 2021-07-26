package juc.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *  Stream流式计算、链式编程、lambda表达式、函数式接口
 *
 */
public class StreamDemo {


    /**
     *  题目要求：一分钟内完成，只能用一行代码
     *  1. Id必须是整数
     *  2. 年龄必须是大于23岁
     *  3. 用户名转为大写
     *  4. 用户名倒序
     *  5. 只输出一个用户
     *
     */
    public static void main(String[] args) {

        User user1 = new User(1, "a", 21);
        User user2 = new User(2, "b", 22);
        User user3 = new User(3, "c", 23);
        User user4 = new User(4, "d", 24);
        User user5 = new User(6, "e", 25);

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);
        System.out.println(users.parallelStream().filter((user) -> {
            return user.getId() % 2 == 0;
        })
                .filter((user) -> user.getAge() > 23)
                .map(user -> user.getName().toUpperCase())
                .sorted(Comparator.naturalOrder())
                .findFirst());

        users.parallelStream().filter((user) -> user.getId() % 2 == 0)
                .filter((user) -> user.getAge() > 23)
                .map(user -> user.getName().toUpperCase())
                .sorted(Comparator.naturalOrder())
                .limit(1)
                .forEach(System.out::println);


    }
}



