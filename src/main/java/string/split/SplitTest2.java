package string.split;

public class SplitTest2 {
    public static void main(String[] args) {
        String helmChartName = "https://arm.sero.gic.ericsson.se/artifactory/proj-exilis-drop-helm/eric-ran-cu-cp-nr/eric-ran-cu-cp-nr-1.13.20-13.tgz";
        System.out.println(getHelmVersionFromHelmChartName(helmChartName));

    }

    public static String getHelmVersionFromHelmChartName(String helmChartName) {

        String[] split = helmChartName.split("/");
        int length = split.length;
        String version = split[length - 1].replace(".tgz", "");
        return version;
    }
}
