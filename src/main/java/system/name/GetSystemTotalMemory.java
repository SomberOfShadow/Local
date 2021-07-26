package system.name;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetSystemTotalMemory {
    public static void main(String[] args) throws IOException {

        String totalCommand = "systeminfo";
        Process process = Runtime.getRuntime().exec(totalCommand);

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder totalPhysicalMemory = new StringBuilder();
        while ((line = br.readLine()) != null) {
            if (line.contains("Total Physical Memory")) {
                totalPhysicalMemory.append(line);
            }
        }

        // result:Total Physical Memory:     32,615 MB
        System.out.println(totalPhysicalMemory.toString());


        String memory = totalPhysicalMemory.toString().split(":")[1];

        // result:     32,615 MB
        System.out.println(memory);


        // Remove blank spaces before and after strings
        String REGEX = "^\\s*|\\s*$";
        String REPLACE = "";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(memory);
        memory = m.replaceAll(REPLACE);

        // result:32,615 MB
        System.out.println(memory);

//        String replace = memory.replace(",", "").split(" ")[0];
        memory = memory.replace(",", "").split(" ")[0];
        int i = Integer.parseInt(memory) * 1024;
        System.out.println(i + " kB");

    }
}
