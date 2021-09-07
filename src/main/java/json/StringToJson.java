package json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @Classname StringToJson
 * @Description TODO
 * @Date 2021-08-04 5:27 PM
 * @Created by eenheni
 */
public class StringToJson {


    public static void main(String[] args) {

        //this string is a list and each element in list is different , thus need to pre-handle first
        String s = "[{\"compute-resources\":{\"hard\":{\"limits.cpu\":\"80\",\"limits.ephemeral-storage\":\"150Gi\",\"limits.memory\":\"128Gi\",\"pods\":\"50\",\"requests.cpu\":\"36\",\"requests.ephemeral-storage\":\"100Gi\",\"requests.memory\":\"128Gi\"},\"used\":{\"limits.cpu\":\"0\",\"limits.ephemeral-storage\":\"0\",\"limits.memory\":\"0\",\"pods\":\"0\",\"requests.cpu\":\"0\",\"requests.ephemeral-storage\":\"0\",\"requests.memory\":\"0\"}}},{\"core-object-counts\":{\"hard\":{\"configmaps\":\"80\",\"replicationcontrollers\":\"0\",\"services\":\"60\"},\"used\":{\"configmaps\":\"3\",\"replicationcontrollers\":\"0\",\"services\":\"0\"}}},{\"secret-counts\":{\"hard\":{\"secrets\":\"400\"},\"used\":{\"secrets\":\"28\"}}},{\"storage-consumption\":{\"hard\":{\"persistentvolumeclaims\":\"20\",\"requests.storage\":\"100Gi\"},\"used\":{\"persistentvolumeclaims\":\"0\",\"requests.storage\":\"0\"}}}]";

        HashMap map = convertStringToMap(s);

//        map.forEach((key, value) -> {
//            System.out.println("key:" + key + "      value:" + value);
//        });

        /**
         * Console output:
         *
         C:\MySoftWare\Java\jdk-11.0.2\bin\java.exe "-javaagent:C:\MySoftWare\IntelliJ IDEA Community Edition 2019.1.2\lib\idea_rt.jar=54481:C:\MySoftWare\IntelliJ IDEA Community Edition 2019.1.2\bin" -Dfile.encoding=UTF-8 -classpath C:\MySoftWare\Java\workspace\Local\target\classes;C:\Users\EENHENI\.m2\repository\org\aspectj\aspectjrt\1.9.6\aspectjrt-1.9.6.jar;C:\Users\EENHENI\.m2\repository\com\google\guava\guava\27.1-jre\guava-27.1-jre.jar;C:\Users\EENHENI\.m2\repository\com\google\guava\failureaccess\1.0.1\failureaccess-1.0.1.jar;C:\Users\EENHENI\.m2\repository\com\google\guava\listenablefuture\9999.0-empty-to-avoid-conflict-with-guava\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;C:\Users\EENHENI\.m2\repository\com\google\code\findbugs\jsr305\3.0.2\jsr305-3.0.2.jar;C:\Users\EENHENI\.m2\repository\org\checkerframework\checker-qual\2.5.2\checker-qual-2.5.2.jar;C:\Users\EENHENI\.m2\repository\com\google\errorprone\error_prone_annotations\2.2.0\error_prone_annotations-2.2.0.jar;C:\Users\EENHENI\.m2\repository\com\google\j2objc\j2objc-annotations\1.1\j2objc-annotations-1.1.jar;C:\Users\EENHENI\.m2\repository\org\codehaus\mojo\animal-sniffer-annotations\1.17\animal-sniffer-annotations-1.17.jar;C:\Users\EENHENI\.m2\repository\com\ericsson\msran\test\msran-test-data-schema\0.0.11\msran-test-data-schema-0.0.11.jar;C:\Users\EENHENI\.m2\repository\org\apache\avro\avro\1.9.2\avro-1.9.2.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\core\jackson-core\2.10.2\jackson-core-2.10.2.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\core\jackson-databind\2.10.2\jackson-databind-2.10.2.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\core\jackson-annotations\2.10.2\jackson-annotations-2.10.2.jar;C:\Users\EENHENI\.m2\repository\org\apache\commons\commons-compress\1.19\commons-compress-1.19.jar;C:\Users\EENHENI\.m2\repository\org\testng\testng\6.14.12\testng-6.14.12.jar;C:\Users\EENHENI\.m2\repository\com\beust\jcommander\1.72\jcommander-1.72.jar;C:\Users\EENHENI\.m2\repository\org\yaml\snakeyaml\1.17\snakeyaml-1.17.jar;C:\Users\EENHENI\.m2\repository\junit\junit\4.12\junit-4.12.jar;C:\Users\EENHENI\.m2\repository\org\hamcrest\hamcrest-core\1.3\hamcrest-core-1.3.jar;C:\Users\EENHENI\.m2\repository\org\apache\ant\ant\1.9.7\ant-1.9.7.jar;C:\Users\EENHENI\.m2\repository\org\apache\ant\ant-launcher\1.9.7\ant-launcher-1.9.7.jar;C:\Users\EENHENI\.m2\repository\org\apache-extras\beanshell\bsh\2.0b6\bsh-2.0b6.jar;C:\Users\EENHENI\.m2\repository\org\slf4j\slf4j-api\1.7.26\slf4j-api-1.7.26.jar;C:\Users\EENHENI\.m2\repository\com\ericsson\commonlibrary\troubleshooter\1.0.4\troubleshooter-1.0.4.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\client\elasticsearch-rest-high-level-client\7.3.1\elasticsearch-rest-high-level-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch\7.3.1\elasticsearch-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch-core\7.3.1\elasticsearch-core-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch-secure-sm\7.3.1\elasticsearch-secure-sm-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch-x-content\7.3.1\elasticsearch-x-content-7.3.1.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-smile\2.8.11\jackson-dataformat-smile-2.8.11.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-yaml\2.8.11\jackson-dataformat-yaml-2.8.11.jar;C:\Users\EENHENI\.m2\repository\com\fasterxml\jackson\dataformat\jackson-dataformat-cbor\2.8.11\jackson-dataformat-cbor-2.8.11.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch-geo\7.3.1\elasticsearch-geo-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\apache\lucene\lucene-core\8.1.0\lucene-core-8.1.0.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\elasticsearch-cli\7.3.1\elasticsearch-cli-7.3.1.jar;C:\Users\EENHENI\.m2\repository\net\sf\jopt-simple\jopt-simple\5.0.2\jopt-simple-5.0.2.jar;C:\Users\EENHENI\.m2\repository\com\carrotsearch\hppc\0.8.1\hppc-0.8.1.jar;C:\Users\EENHENI\.m2\repository\joda-time\joda-time\2.10.2\joda-time-2.10.2.jar;C:\Users\EENHENI\.m2\repository\com\tdunning\t-digest\3.2\t-digest-3.2.jar;C:\Users\EENHENI\.m2\repository\org\hdrhistogram\HdrHistogram\2.1.9\HdrHistogram-2.1.9.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\jna\4.5.1\jna-4.5.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\client\elasticsearch-rest-client\7.3.1\elasticsearch-rest-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\apache\httpcomponents\httpasyncclient\4.1.4\httpasyncclient-4.1.4.jar;C:\Users\EENHENI\.m2\repository\org\apache\httpcomponents\httpcore-nio\4.4.11\httpcore-nio-4.4.11.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\plugin\parent-join-client\7.3.1\parent-join-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\plugin\aggs-matrix-stats-client\7.3.1\aggs-matrix-stats-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\plugin\rank-eval-client\7.3.1\rank-eval-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\org\elasticsearch\plugin\lang-mustache-client\7.3.1\lang-mustache-client-7.3.1.jar;C:\Users\EENHENI\.m2\repository\com\github\spullara\mustache\java\compiler\0.9.3\compiler-0.9.3.jar;C:\Users\EENHENI\.m2\repository\commons-io\commons-io\2.6\commons-io-2.6.jar;C:\Users\EENHENI\.m2\repository\org\apache\maven\maven-model\3.5.4\maven-model-3.5.4.jar;C:\Users\EENHENI\.m2\repository\org\codehaus\plexus\plexus-utils\3.1.0\plexus-utils-3.1.0.jar;C:\Users\EENHENI\.m2\repository\org\json\json\20190722\json-20190722.jar;C:\Users\EENHENI\.m2\repository\org\apache\httpcomponents\httpclient\4.5.9\httpclient-4.5.9.jar;C:\Users\EENHENI\.m2\repository\org\apache\httpcomponents\httpcore\4.4.11\httpcore-4.4.11.jar;C:\Users\EENHENI\.m2\repository\commons-codec\commons-codec\1.11\commons-codec-1.11.jar;C:\Users\EENHENI\.m2\repository\com\google\code\gson\gson\2.8.6\gson-2.8.6.jar;C:\Users\EENHENI\.m2\repository\org\apache\logging\log4j\log4j-slf4j-impl\2.11.2\log4j-slf4j-impl-2.11.2.jar;C:\Users\EENHENI\.m2\repository\org\apache\logging\log4j\log4j-api\2.11.2\log4j-api-2.11.2.jar;C:\Users\EENHENI\.m2\repository\org\apache\logging\log4j\log4j-core\2.11.2\log4j-core-2.11.2.jar;C:\Users\EENHENI\.m2\repository\ch\ethz\ganymed\ganymed-ssh2\build210\ganymed-ssh2-build210.jar;C:\Users\EENHENI\.m2\repository\org\apache\commons\commons-lang3\3.9\commons-lang3-3.9.jar;C:\Users\EENHENI\.m2\repository\commons-logging\commons-logging\1.2\commons-logging-1.2.jar;C:\Users\EENHENI\.m2\repository\org\jsoup\jsoup\1.13.1\jsoup-1.13.1.jar;C:\Users\EENHENI\.m2\repository\org\apache\kafka\kafka-clients\2.7.0\kafka-clients-2.7.0.jar;C:\Users\EENHENI\.m2\repository\com\github\luben\zstd-jni\1.4.5-6\zstd-jni-1.4.5-6.jar;C:\Users\EENHENI\.m2\repository\org\lz4\lz4-java\1.7.1\lz4-java-1.7.1.jar;C:\Users\EENHENI\.m2\repository\org\xerial\snappy\snappy-java\1.1.7.7\snappy-java-1.1.7.7.jar;C:\Users\EENHENI\.m2\repository\mysql\mysql-connector-java\5.1.29\mysql-connector-java-5.1.29.jar json.StringToJson
         key:compute-resources.used.limits.ephemeral-storage      value:0
         key:compute-resources.used.requests.cpu      value:0
         key:storage-consumption.used.requests.storage      value:0
         key:compute-resources.used.limits.cpu      value:0
         key:compute-resources.used.requests.ephemeral-storage      value:0
         key:compute-resources.hard.requests.ephemeral-storage      value:100Gi
         key:storage-consumption.used.persistentvolumeclaims      value:0
         key:secret-counts.hard.secrets      value:400
         key:core-object-counts.hard.replicationcontrollers      value:0
         key:core-object-counts.hard.services      value:60
         key:secret-counts.used.secrets      value:28
         key:compute-resources.hard.requests.memory      value:128Gi
         key:compute-resources.hard.limits.cpu      value:80
         key:compute-resources.used.requests.memory      value:0
         key:compute-resources.hard.pods      value:50
         key:storage-consumption.hard.persistentvolumeclaims      value:20
         key:compute-resources.hard.requests.cpu      value:36
         key:core-object-counts.hard.configmaps      value:80
         key:core-object-counts.used.services      value:0
         key:storage-consumption.hard.requests.storage      value:100Gi
         key:compute-resources.used.limits.memory      value:0
         key:core-object-counts.used.configmaps      value:3
         key:core-object-counts.used.replicationcontrollers      value:0
         key:compute-resources.hard.limits.ephemeral-storage      value:150Gi
         key:compute-resources.hard.limits.memory      value:128Gi
         key:compute-resources.used.pods      value:0

         Process finished with exit code 0

         *
         *
         */

    }

    private static HashMap convertStringToMap(String s){
        HashMap<Object, Object> map = new HashMap<>();

        JSONArray jsonArray = new JSONArray(s);
        for (Object o : jsonArray) {
            JSONObject jsonObject = (JSONObject) o;

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()){
                String key = keys.next();
                JSONObject value = (JSONObject)jsonObject.get(key);

                Iterator<String> hardAndUsed = value.keys();
                while (hardAndUsed.hasNext()) {
                    String hardOrUsed = hardAndUsed.next();
                    JSONObject o1 = (JSONObject) value.get(hardOrUsed);

                    o1.toMap().forEach((k, v) -> {
                        String targetKey = convert((key + "." + hardOrUsed + "." + k).replace("-", "."));
                        map.put(targetKey, v);
                    });

//                    if ("hard".equals(hardOrUsed)) {
//                        System.out.println("hard:" + o1);
//                    } else if ("used".equals(hardOrUsed)) {
//                        System.out.println("used:" + o1);
//                    }
                }

            }

        }
        return map;
    }


    private static String convert(String s) {

        String[] split = s.split("\\.");
        String target = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            if (i == 0) {
                target += split[0];
                stringBuilder.append(split[0]);
            } else if (i > 0) {
               stringBuilder.append(split[i].substring(0,1).toUpperCase() + split[i].substring(1));
               target += split[i].substring(0,1).toUpperCase() + split[i].substring(1);
            }
        }

//        System.out.println("target: " + target);
        System.out.println("target: " + stringBuilder.toString());
        return target;
    }


}
