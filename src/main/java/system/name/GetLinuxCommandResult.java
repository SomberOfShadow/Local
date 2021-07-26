package system.name;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;


public class GetLinuxCommandResult {


    public static void main(String[] args) {


//        String ip = "134.138.145.221";
//        String userName = "eenheni";
//        String password = "Shadow##19921211";
//
//        String cmd = "find /etc/*-release";
//        execute(userName, password, ip, cmd);

        getLinuxCommandOutout();
    }


    public static String execute(String userName, String password, String ipAddr, String cmd) {
        String result = "";
        boolean isAuthed = false;
        try {
            if (InetAddress.getByName(ipAddr).isReachable(1500)) {
                Connection conn = new Connection(ipAddr);
                conn.connect();
                isAuthed = conn.authenticateWithPassword(userName, password);
                if (isAuthed) {
                    Session session = conn.openSession();//打开一个会话
                    session.execCommand(cmd);
                    result = processStdout(session.getStdout());
                    //如果为得到标准输出为空，说明脚本执行出错了

                    if (StringUtils.isBlank(result)) {
//                        directory.info("得到标准输出为空,链接conn:" + conn + ",执行的命令：" + cmd);
                        System.out.println("得到标准输出为空,链接conn:" + conn + ",执行的命令：" + cmd);
                        result = processStdout(session.getStderr());
                    } else {
//                        directory.info("执行命令成功,链接conn:" + conn + ",执行的命令：" + cmd);
                        System.out.println("得到标准输出为空,链接conn:" + conn + ",执行的命令：" + cmd);
                    }

                    session.close();
                    conn.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private static String processStdout(InputStream in) {
        InputStream stdout = new StreamGobbler(in);
        StringBuilder buffer = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(line).append("\n");
            }
        } catch (IOException e) {
//            directory.error("解析脚本出错：" + e.getMessage());
            System.out.println("解析脚本出错：" + e.getMessage());
        }
        return buffer.toString();
    }


    public static String getLinuxCommandOutout() {
        StringBuffer sb = new StringBuffer();
        //            String[] cmd = new String[]{"/bin/sh", "-c", "ls -l"};
        String[] cmd = new String[]{"/bin/sh", "-c", "find /etc/*-release"};

        String command = "find /etc/*-release";
        try {
            Process ps = Runtime.getRuntime().exec(command);

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            String result = sb.toString();

            System.out.println("result is : " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }


}


