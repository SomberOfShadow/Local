package iostream.bio.demo04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Classname ServerRunnableTarget
 * @Description
 * 把server的socket对象封装成一个Runnable 对象
 * @Date 2021-07-14 4:37 PM
 * @Created by eenheni
 */
public class ServerRunnableTarget implements Runnable {
    private Socket socket;

    public ServerRunnableTarget(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //从socket对象中拿到字节流
        InputStream is = null;
        try {
            is = socket.getInputStream();
            // 包装成字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String msg;
            while ((msg = bufferedReader.readLine()) != null){
                System.out.println("服务器端接收到的消息是：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
