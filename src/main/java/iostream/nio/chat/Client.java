package iostream.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Classname Client
 * @Description
 * 目标: 客户端代码逻辑实现
 * @Date 2021-07-21 4:24 PM
 * @Created by eenheni
 */
public class Client {

    //1. 定义属性
    private Selector selector;
    private static final int PORT = 9999;
    private SocketChannel socketChannel;

    public Client() {
        try {
            //a. 创建选择器
            selector = Selector.open();

            //b. connect server
            socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", PORT));

            // c. set no blocking mode
            socketChannel.configureBlocking(false);

            socketChannel.register(selector, SelectionKey.OP_READ);

            System.out.println("当前客户端准备完成！");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client();

        //定义一个线程专门监听服务器发过来的消息事件
        new Thread(()->{
            try {
                while (true){//或者在readInfo()中使用死循环实时监听
                    client.readInfo();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入:");
        while (scanner.hasNext()){
            String s = scanner.nextLine();
            client.sendToServer(s);
        }
    }

    private void sendToServer(String s) {

        try {
            socketChannel.write(ByteBuffer.wrap(("波仔说:" + s).getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readInfo() throws IOException {
        if (selector.select() > 0)  {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                SelectionKey key = iterator.next();

                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel)key.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    channel.read(buffer);

                    System.out.println("收到服务端的消息：" + new String(buffer.array()).trim());
                }
                iterator.remove();
            }

        }
    }
}
