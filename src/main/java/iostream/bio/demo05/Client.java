package iostream.bio.demo05;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @Classname Client
 * @Description
 * 目标：实现客户端上传任意类型的文件数据到服务端存储
 *
 * 总结：
 * 怎么发，怎么收，流是对应使用的
 * 发完需要通知，否则一直等待
 *
 * @Date 2021-07-15 10:48 AM
 * @Created by eenheni
 */
public class Client {
    public static void main(String[] args) {

        try( InputStream is = new FileInputStream("C:\\MySoftWare\\Java\\workspace\\system_and_name\\src\\main\\resources\\version.txt")) {

            //1. 请求与服务器的socket连接
            Socket socket = new Socket("127.0.0.1", 888);

            //2. 把字节输出流包装成一个数据输出流(数据输出流可以先发一个文件类型再发文件内容)
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            //3. 文件后缀名发送给服务器
            dos.writeUTF(".txt");//实际开发过程中可以获取文件名进行截取获得后缀名
            //4. 文件数据发送给服务器

            byte[] buffer = new byte[1024];//一个字节一个字节进行读取
            int length;

            while ((length = is.read(buffer)) > 0){
                dos.write(buffer, 0, length);
            }

            dos.flush();
            //通知服务端发送完毕， 否则服务端一直等待，客户端关闭则服务端抛出异常：connection reset
            socket.shutdownOutput();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
