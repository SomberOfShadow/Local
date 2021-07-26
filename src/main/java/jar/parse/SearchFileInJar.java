package jar.parse;


import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SearchFileInJar {

    public static String pomPath = "";
    public static void main(String[] args) throws IOException {

//        String jarPath = "C:\\MySoftWare\\Java\\workspace\\msran-test-documentation\\target\\msran-test-documentation-0.0.60-SNAPSHOT.jar";

//        String jarPath = "C:\\MySoftWare\\jd-gui-windows-1.6.6\\jar\\msran-regression-tests-1.8.7256.jar";

//        String jarPath = "C:\\MySoftWare\\jd-gui-windows-1.6.6\\jar\\test-lrat-jcat.jar";
//        String jarPath = "C:\\MySoftWare\\jd-gui-windows-1.6.6\\jar\\MOFWK-jcat.jar";
        String jarPath = "C:\\MySoftWare\\jd-gui-windows-1.6.6\\jar\\mofwk-jcat-black-v2.jar";


        System.out.println("jar path is " + jarPath);

//        String repoName = "msran-test-documentation";
//        String repoName = "com.ericsson.msr.tests.mofwk";

//        parseJar(jarPath,repoName + "/pom.xml");
        getPomPathFromJar(jarPath);
        if (!pomPath.isEmpty()) {
            System.out.println("pom path is not empty.");
        }
    }

    private static void getPomPathFromJar(String path) throws IOException {
//        Pattern maxProcPattern = Pattern
//                .compile(
//                        "(?m)^META-INF/maven/*/.+/msran-test-documentation/pom\\.xml");


        Pattern maxProcPattern = Pattern
                .compile(
                        "(?m)^META-INF/maven/((com\\.ericsson\\.msr\\.lrat\\.test)|(com\\.ericsson\\.msran\\.jcat)|(com\\.ericsson\\.radio\\.test)|(com\\.ericsson\\.msran\\.test)|(com\\.ericsson\\.wrbs\\.ci\\.oam)|(com\\.ericsson\\.wrbs\\.ci\\.wm))/.+/pom\\.xml");
        parseJar(maxProcPattern, path, "pom.xml");
    }

    // match a file by using a regex pattern, but we need to get test repo name
    public static void parseJar(Pattern pattern, String path, String folder) throws IOException {
        try (JarFile jf = new JarFile(path)) {
            Enumeration<JarEntry> entries = jf.entries();
            boolean foundPomPath = false;
            while (entries.hasMoreElements() && !foundPomPath) {
                JarEntry je = entries.nextElement();
                String jeName = je.getName();
                System.out.println("Each file path is - " + jeName);

                Matcher matcher = pattern.matcher(jeName);

                if (matcher.find()) {
                    pomPath = jeName;
                    foundPomPath = true;
                    System.out.println("It can be found in JAR file, path is " + jeName);
                }

            }
//            if (!foundPomPath && !path.contains("MOFWK-jcat.jar")) {
////                pomPath = "META-INF/maven/com.ericsson.msran.jcat/msran-jcat-extension/" + folder;
////                JcatLoggingApi.addCustomReport("Test repo info: ", "unknown");
//                System.out.println("Test repo info: unknown");
//            }
//
//            if (!foundPomPath && path.contains("MOFWK-jcat.jar")) {
////                pomPath = "META-INF/maven/com.ericsson.msran.jcat/msran-jcat-extension/" + folder;
////                JcatLoggingApi.addCustomReport("Test repo info: ", "MOFWK_31");
//                System.out.println("Test repo info: MOFWK_31");
//            }

        }
    }

    // match a file in JAR file by using contains() method of String
    public static void parseJar(String path, String folder) throws IOException {
        System.out.println("folder is " + folder);
        try (JarFile jf = new JarFile(path)) {
            Enumeration<JarEntry> entries = jf.entries();
            boolean foundPomPath = false;
            while (entries.hasMoreElements() && !foundPomPath) {
                JarEntry je = entries.nextElement();
                String jeName = je.getName();
                System.out.println("Each file path is - " + jeName);

                if(jeName.contains(folder)) {
                    System.out.println("It can be found in JAR file, path is " + jeName);
                    pomPath = jeName;
                    foundPomPath = true;

                }

            }
            if (!foundPomPath) {
                pomPath = "META-INF/maven/com.ericsson.msran.jcat/msran-jcat-extension/" + folder;
            }

        }
    }

}
