package iostream.nio.selector.demo01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Classname Client
 * @Description
 * 目标：基于NIO客户端实现
 * @Date 2021-07-21 11:29 AM
 * @Created by eenheni
 */
public class Client {
    public static void main(String[] args) throws IOException {
        //1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(
                new InetSocketAddress("127.0.0.1", 9999));
        //2. 切换成非阻塞模式
        socketChannel.configureBlocking(false);

        //3. 分配指定缓冲区大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4. 发送数据给服务端
        Scanner scanner = new Scanner(System.in);

        while (true){
            System.out.println("pls input:");
            String msg = scanner.nextLine();
            buffer.put(msg.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }
    }
}
