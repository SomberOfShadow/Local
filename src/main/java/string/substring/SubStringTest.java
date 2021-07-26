package string.substring;

public class SubStringTest {
    public static void main(String[] args) {
        String version1 = "1.8.1234";
        String version2 = "1.9.1234";
        System.out.println(getRstate(version1));
        System.out.println(getRstate(version2));

        //substring

        String s = "2018-04-16 20:31:50.0 UTC";
        String substring = s.substring(0, 19);
        System.out.println(substring);
    }

    public static String getRstate(String version){
        String rstate = "";
        if (version.startsWith("1.8")) {
            rstate = "R1A" + version.substring(4);
        } else if (version.startsWith("1.9")) {
            rstate = "R1B" + version.substring(4);
        }
        return rstate;
    }



}
