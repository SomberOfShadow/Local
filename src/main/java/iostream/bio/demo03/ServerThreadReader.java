package iostream.bio.demo03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @Classname ServerThreadReader
 * @Description TODO
 * @Date 2021-07-12 4:20 PM
 * @Created by eenheni
 */
public class ServerThreadReader extends Thread {

    private Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //从socket对象中拿到字节流
            InputStream is = socket.getInputStream();
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
