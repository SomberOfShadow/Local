package string.substring;

import org.apache.commons.lang3.StringUtils;

/**
 * @Classname SubStringTest2
 * @Description TODO
 * @Date 2021-08-20 11:47 AM
 * @Created by eenheni
 */
public class SubStringTest2 {
    public static void main(String[] args) {

        String s = "https://fem10s10-eiffel144.eiffel.gic.ericsson.se:8443/jenkins/job/EDC-RB-MJE-CXP9032589_8-main/1682/";

        String sub = StringUtils.substringAfter(s, "job/");

        System.out.println(sub);
        String s1 = sub.replace("-main", "").split("\\/")[0];
        String[] split = s1.split("-");
        System.out.println(split[split.length - 1]);

        System.out.println("===========================");
        System.out.println(StringUtils.substringAfter(s, "MJE-"));
        String s2 = StringUtils.substringAfter(s, "MJE-");
        System.out.println(StringUtils.substringBefore(s2, "-main"));


    }
}
