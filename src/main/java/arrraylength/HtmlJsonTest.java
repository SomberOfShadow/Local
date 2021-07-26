package arrraylength;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static org.apache.commons.io.FileUtils.openOutputStream;

public class HtmlJsonTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlJsonTest.class);
    public static void main(String[] args) {

        Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        Gson GSON_PRETTY_NO_DISABLEHTML = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = "{\n" +
                "  \"_index\": \"qa-dev.bat-alert\",\n" +
                "  \"_type\": \"doc\",\n" +
                "  \"_id\": \"8AkfFHUBnwX63RhE9-z8\",\n" +
                "  \"_version\": 1,\n" +
                "  \"_score\": 2,\n" +
                "  \"_source\": {\n" +
                "    \"eventId\": \"bbe4914f-794e-49fc-ae9c-cda56c36221e\",\n" +
                "    \"eventData\": {\n" +
                "      \"severity\": \"MINOR\",\n" +
                "      \"announcementIdentifiers\": [\n" +
                "        \"BAT-ALERT\"\n" +
                "      ],\n" +
                "      \"logReferences\": {},\n" +
                "      \"flowContext\": \"\",\n" +
                "      \"announcementId\": \"a308dd4f-6d1c-4945-9d4a-1164f4515d6c\",\n" +
                "      \"header\": \"STABILITY_ANNOUNCEMENT\",\n" +
                "      \"body\": \"STABILITY_EVENT\",\n" +
                "      \"url\": \"\",\n" +
                "      \"optionalParameters\": {\n" +
                "        \"subject\": \"NEW RESTART DETECTED ON STABTDD4B seroitrbs00201\",\n" +
                "        \"clusterName\": \"STABTDD4B\",\n" +
                "        \"testCaseName\": \"BatCrashSignatureMonitor\",\n" +
                "        \"message\": \"STP Name= STABTDD4B StpClusterType= 4G Node= seroitrbs00201 Time= 2020-10-10 19:51:19 UniqueCrashRef = 80cc57eb-f39b-4b54-9a09-b482777821e9 MsranUP= CXP2010174/1_R16A80 msranUpRelease= 20.Q4 Restart Reason= Program Crash Restart Type= fru_2048 Restart rank= Cold Extra= - Restart Information=  Trace Information=  Program= - Pid= - Process=  Product Info=  OSE Component=  Error Description=  Error Code=  Error Information=  PMD= - PMD Identity=  \",\n" +
                "        \"campaignName\": \"Release_STABTDD4B_LMT\",\n" +
                "        \"tgfId\": \"34623124\",\n" +
                "        \"timestamp\": \"2020-10-10T20:07:20.247Z\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"eiffel.eventTime\": \"2020-10-10T20:07:20.811Z\",\n" +
                "    \"inputEventIds\": [\n" +
                "      \"15d76831-f227-4bf0-8f67-c3ebda04c321\"\n" +
                "    ],\n" +
                "    \"eventType\": \"EiffelAnnouncementEvent\",\n" +
                "    \"domainId\": \"eiffel003.seki.jcat.qa.stability\"\n" +
                "  },\n" +
                "  \"fields\": {\n" +
                "    \"eventData.optionalParameters.timestamp\": [\n" +
                "      \"2020-10-10T20:07:20.247Z\"\n" +
                "    ],\n" +
                "    \"eiffel.eventTime\": [\n" +
                "      \"2020-10-10T20:07:20.811Z\"\n" +
                "    ]\n" +
                "  }\n" +
                "}";
        File file = new File("C:\\temp\\test.json");

        String s = GSON_PRETTY.toJson(jsonString);
        try {
            writeStringToFile(file, s, Charset.defaultCharset(), false);
            LOGGER.info("Succeed to write json to file!");
        } catch (IOException e) {
            LOGGER.error("Fail to write json to file!");
        }


        File file_no = new File("C:\\temp\\test_no.json");
        String str = GSON_PRETTY_NO_DISABLEHTML.toJson(jsonString);
        try {
            writeStringToFile(file_no, str, Charset.defaultCharset(), false);
            LOGGER.info("Succeed to write json to file!");
        } catch (IOException e) {
            LOGGER.error("Fail to write json to file!");
        }

    }

    public static void writeStringToFile(final File file, final String data, final Charset charset,
                                         final boolean append) throws IOException {
        try (OutputStream out = openOutputStream(file, append)) {
            IOUtils.write(data, out, charset);
        }
    }
}
