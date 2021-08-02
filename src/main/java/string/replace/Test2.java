package string.replace;

/**
 * @Classname Test2
 * @Description TODO
 * @Date 2021-08-02 5:34 PM
 * @Created by eenheni
 */
public class Test2 {
    public static void main(String[] args) {
        String s = "G2RbsResource_$$_";

        String replace = s.split("\\$")[0].replace("Resource", "")
                .replace("_", "");

        System.out.println(replace);
    }
}
