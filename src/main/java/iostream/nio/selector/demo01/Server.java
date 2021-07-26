package iostream.nio.selector.demo01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Classname Server
 * @Description
 *  目标：NIO模式下通信的入门案例，服务端开发
 * @Date 2021-07-21 10:59 AM
 * @Created by eenheni
 */
public class Server {

    public static void main(String[] args) throws IOException {
        System.out.println("Server Start-----------------");
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();

        //2. 切换为非阻塞模式
        ssChannel.configureBlocking(false);

        //3. 绑定端口
        ssChannel.bind(new InetSocketAddress(9999));

        //4. 获取选择器
        Selector selector = Selector.open();

        //5. 将通道注册到选择器上，并且开始指定监听事件
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        //6.使用Selector轮询已经就绪好的事件
        while (selector.select() > 0) {
            
            //7. 获取选择器中所有注册好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            
            //8. 遍历事件
            while (it.hasNext()){
                
                //9. 判断当前事件
                SelectionKey sk = it.next();
                if (sk.isAcceptable()) {
                    //10. 直接获取当前接入的客户端通道

                    SocketChannel schannel = ssChannel.accept();
                    schannel.configureBlocking(false);

                    //11. 将本科客户端通道注册到选择器
                    schannel.register(selector, SelectionKey.OP_READ);  // 注册到selector，等待连接

                } else if (sk.isReadable()) {
                    //13. 获取当前选择器上的读事件
                    SocketChannel channel = (SocketChannel)sk.channel();

                    //14. 读取数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int len = 0;

                    while ((len = channel.read(buffer))> 0 ) {
                        buffer.flip();
                        System.out.println(new String (buffer.array(), 0, len));
                        buffer.clear();
                    }
                }
                it.remove();//处理完毕移除当前事件
            }

        }
    }
}
