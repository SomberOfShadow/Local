package jar.parse;

public class SplitStringLinux {
    public static void main(String[] args) {

        getRepoName();
    }

    public static String getRepoName () {
        //  /home/edebwoj/JCAT/wmr-common-test/target/wmr-common-test-0.0.122-SNAPSHOT.jar
        // --> wmr-common-test
        // /proj/tgf_ki2/cache/mje/CXP2020011_1-R85A07P1A4666/jvm/lib/msran-regression-tests-1.8.7289.jar
        // --> msran-regression-tests

        String repoNameString = "/proj/tgf_ki2/cache/mje/CXP2020011_1-R85A07P1A4666/jvm/lib/msran-regression-tests-1.8.7289.jar";


        int length = repoNameString.split("/").length;
        System.out.println("length is " + length);
        System.out.println(repoNameString.split("/")[1]);
        String[] split = repoNameString.split("/")[length - 1].split("-");
        StringBuilder repoName = new StringBuilder();

        repoName.append(split[0]).append("-").append(split[1]).append("-").append(split[2]);
        System.out.println(repoName.toString());


        return "";
    }

}
