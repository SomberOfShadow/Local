package iostream.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Classname Server
 * @Description
 * 目标：服务端群聊系统的实现
 * @Date 2021-07-21 2:19 PM
 * @Created by eenheni
 */
public class Server {

    //1. 定义一些成员属性
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9999;

    //2. 定义初始化代码逻辑

    public Server() {
        try {
            //a. 创建选择器
            selector = Selector.open();
            //b. 获取通道
            serverSocketChannel = ServerSocketChannel.open();
            //c. 绑定端口
            serverSocketChannel.bind(new InetSocketAddress(PORT));
            //d. 设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //e. 把通道注册到选择器上，并指定接收事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //创建服务器监听
        Server server = new Server();
        //开始监听
        server.listen();
    }

    private void listen() {

        try {
            while (selector.select() > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while (iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    if (sk.isAcceptable()) {
                        //客户端接入请求，获取客户端当前通道
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //注册成非阻塞模式
                        socketChannel.configureBlocking(false);
                        //注册给选择器，监听读数据
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (sk.isReadable()){
                        //处理客户端的消息，接收它然后实现转发逻辑
                        readClientData(sk);
                    }
                    iterator.remove();
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  接收当前客户端通道，转发给其他的全部客户端通道
     *
     * @param
     * @param sk:
     * @return void
     * @exception
     */
    private void readClientData(SelectionKey sk) {
        SocketChannel channel = null;
        try {
            //直接获取当前客户端通道
            channel = (SocketChannel) sk.channel();
            //创建缓冲区接收客户端数据
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            if (count > 0) {
                //提取读取的数据
                buffer.flip();

                String msg = new String(buffer.array(), 0, buffer.remaining());
                System.out.println("接收到客户端消息：" + msg);
                sendMsgToAllClient(msg, channel);
            }
        } catch (Exception e) {

            try {
                System.out.println("客户端有人离线了:" + channel.getRemoteAddress());
                //代表当前客户端离线
                sk.cancel();//取消注册
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     *  把当前客户端的消息推送给全部在线注册的channel
     *
     * @param msg:
     * @param channel:
     */
    private void sendMsgToAllClient(String msg, SocketChannel channel) {

        System.out.println("服务端开始转发消息:" +  Thread.currentThread().getName());
        for (SelectionKey key : selector.keys()) {

            Channel keyChannel = key.channel();

            //不要把数据发给自己
            if(keyChannel instanceof SocketChannel && channel != keyChannel){//是否可以使用channel.getRemoteAddress()判断？

                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());//相当于把数据put进一个缓冲区
                try {
                    ((SocketChannel)keyChannel).write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
