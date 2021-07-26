package iostream.bio.chat.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * @Classname ServerReaderThread
 * @Description TODO
 * @Date 2021-07-15 2:25 PM
 * @Created by eenheni
 */
public class ServerReaderThread extends Thread{
    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            //1. 从socket中获取当前客户端的输入流，字节输入流--> 字符输入流--> 缓冲字符输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null){

                //2. 服务器收到消息之后，需要将消息推送给当前所有在线的socket
                sendMsgToAllClient(msg);
            }
        } catch (IOException e) {//因为是BIO模式下，一直在监听，出现异常即当前线程下线
            System.out.println("当前有人下线！");
            Server.allSocketsOnline.remove(socket);
        }
    }


    public void sendMsgToAllClient(String msg) {
        PrintStream ps = null;
        for (Socket socket : Server.allSocketsOnline) {
            try {
                ps = new PrintStream(socket.getOutputStream());
                ps.println(msg);
                ps.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
