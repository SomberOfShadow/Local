package cpu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
import java.util.*;

public class CpuUtilizationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger("CpuUtilizationTest.class");
    public static void main(String[] args) {
//        OperatingSystemMXBean osMxBean = ManagementFactory.getOperatingSystemMXBean();
//        double cpu = osMxBean.getSystemLoadAverage();
//        LOGGER.info("cpu utilization is {}", cpu);
        cpuUsage();
    }

    /**
     * 功能：获取Linux系统cpu使用率
     * */
    public static int cpuUsage() {
        Map<?, ?> map1 = cpuinfo1();
//            Thread.sleep(5 * 1000);
        Map<?, ?> map2 = cpuinfo2();

        long user1 = Long.parseLong(map1.get("user").toString());
        long nice1 = Long.parseLong(map1.get("nice").toString());
        long system1 = Long.parseLong(map1.get("system").toString());
        long idle1 = Long.parseLong(map1.get("idle").toString());

        long iowait1 = Long.parseLong(map1.get("iowait").toString());
        long irq1 = Long.parseLong(map1.get("irq").toString());
        long softirq1 = Long.parseLong(map1.get("softirq").toString());
        long stealstolen1 = Long.parseLong(map1.get("stealstolen").toString());


        long user2 = Long.parseLong(map2.get("user").toString());
        long nice2 = Long.parseLong(map2.get("nice").toString());
        long system2 = Long.parseLong(map2.get("system").toString());
        long idle2 = Long.parseLong(map2.get("idle").toString());
        long iowait2 = Long.parseLong(map2.get("iowait").toString());
        long irq2 = Long.parseLong(map2.get("irq").toString());
        long softirq2 = Long.parseLong(map2.get("softirq").toString());
        long stealstolen2 = Long.parseLong(map2.get("stealstolen").toString());

        long total1 = user1 + nice1 + system1 + idle1 + iowait1 + irq1 + softirq1 + stealstolen1;
        long total2 = user2 + nice2 + system2 + idle2 + iowait2 + irq2 + softirq2 + stealstolen2;
        float total = total2 - total1;
        LOGGER.info("total is {}", total);

        long totalIdle1 = idle1;
        long totalIdle2 = idle2;
        float idle = totalIdle2 - totalIdle1;
        LOGGER.info("idel is {}", idle);

        float cpusage = (total - idle) / total;
        LOGGER.info("cpu usage is {}", cpusage);
//
//        long total1 = user1 + system1 + nice1;
//        long total2 = user2 + system2 + nice2;
//        float total = total2 - total1;
//
//        long totalIdle1 = user1 + nice1 + system1 + idle1;
//        long totalIdle2 = user2 + nice2 + system2 + idle2;
//        float totalidle = totalIdle2 - totalIdle1;
//
//        float cpusage = (total / totalidle);

        DecimalFormat df = new DecimalFormat("0.00%");
        String r = df.format(cpusage);
        LOGGER.info("usage is {}", r);

        return (int) cpusage;
    }

    /**
     * 功能：CPU使用信息
     * */
    public static Map<?, ?> cpuinfo1() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("C:\\MySoftWare\\Java\\workspace\\system_and_name\\src\\main\\resources\\stat1"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

    public static Map<?, ?> cpuinfo2() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("C:\\MySoftWare\\Java\\workspace\\system_and_name\\src\\main\\resources\\stat2"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }

}
