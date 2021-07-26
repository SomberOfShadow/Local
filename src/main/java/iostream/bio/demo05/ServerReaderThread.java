package iostream.bio.demo05;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

/**
 * @Classname ServerReaderThread
 * @Description TODO
 * @Date 2021-07-15 11:16 AM
 * @Created by eenheni
 */
public class ServerReaderThread extends Thread {

    private Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            //1. 将字节输入流包装成数据输出流
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            //2. 读取客户端发来的后缀名
            String suffix = dis.readUTF();
            System.out.println("Reveive suffix successfully:" + suffix);

            //3. 定义一个字节输出流的管道负责把客户端的文件数据写出去
            OutputStream os = new FileOutputStream("C:\\MySoftWare\\Java\\workspace\\system_and_name\\src\\main\\resources\\" + UUID.randomUUID().toString() + suffix);

            //4. 从字节输入流读取文件数据，写到字节输出流中去
            byte[] buffer = new byte[1024];
            int length;

            while ((length = dis.read(buffer)) > 0){
                os.write(buffer, 0, length);
            }
            os.close();
            System.out.println("Save file successfully!");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
