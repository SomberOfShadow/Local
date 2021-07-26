package iostream.bio.demo04;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Classname Client
 * @Description TODO
 * @Date 2021-07-12 3:46 PM
 * @Created by eenheni
 */
public class Client {
    public static void main(String[] args) {

        try {
            //1. 创建Socket对象请求服务连接
            Socket socket = new Socket("127.0.0.1", 9999);
            //2. 从Socket中获取输出流
            OutputStream os = socket.getOutputStream();
            //3. 将输出流包装成打印流
            PrintStream printStream = new PrintStream(os);


            Scanner scanner = new Scanner(System.in);
            //4. 使用循环不断的发送消息
            while (true){
                System.out.print("please input:");
                String message = scanner.nextLine();
                printStream.println(message);
                printStream.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
