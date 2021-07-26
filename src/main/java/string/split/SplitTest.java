package string.split;

public class SplitTest {
    public static void main(String[] args) {
        String s1 = "eiffel171.sero1.jcat.qa.MSR_MULTI_JCAT";
        String s2 = "eiffel003.seki.jcat.qa.msr_perf_jcat";

        boolean msr_multi_jcat = s1.toLowerCase().contains("MSR_MULTI_JCAT".toLowerCase());
        System.out.println(msr_multi_jcat);

        int length = s1.split("\\.").length;
        System.out.println(length);
        String s = s1.split("\\.")[s1.split("\\.").length - 1];
        System.out.println(s);
    }
}
