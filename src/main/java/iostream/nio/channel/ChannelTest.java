package iostream.nio.channel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname ChannelTest
 * @Description
 * 目标：
 *      使用channel进行文件读写操作
 * @Date 2021-07-16 10:21 AM
 * @Created by eenheni
 * {@link iostream.nio.channel}
 */

public class ChannelTest {


    @Test
    public void test02() throws IOException {
        //1. 字节输入流
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();

        //2. bytes output stream
        FileOutputStream os = new FileOutputStream("data03.txt");
        FileChannel osChannel = os.getChannel();

        //3. 使用transferFrom/transferTo复制数据

        osChannel.transferFrom(isChannel, isChannel.position(), isChannel.size());
        //或者使用下面
//        isChannel.transferTo(isChannel.position(), isChannel.size(), osChannel);

        isChannel.close();
        osChannel.close();
        System.out.println("使用transferFrom复制数据成功！");

    }
    @Test
    // test data scatter and aggregation
    public void test() throws Exception {
        //1. 字节输入流
        FileInputStream is = new FileInputStream("data01.txt");
        FileChannel isChannel = is.getChannel();
        //2. bytes output stream

        FileOutputStream os = new FileOutputStream("data02.txt");
        FileChannel osChannel = os.getChannel();

        //3. define multi buffer to scatter data
        ByteBuffer buffer1 = ByteBuffer.allocate(4);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] buffers = {buffer1, buffer2};

        /**
         *
         *  4. 读物数据到缓冲区数组, 可能缓冲区大小不够，
         * 因此实际中可能需要尽心个循环写入，参考{@link iostream.nio.channel.ChannelTest#copy()}
         * 同类方法：{@link #copy()}
         * 不同类方法：{@link iostream.nio.channel.ChannelTest#copy()}
         */

        isChannel.read(buffers);
        //5. 从每个缓冲区中查看是否有数据
        for (ByteBuffer buffer : buffers) {
            buffer.flip();
            System.out.println(new String (buffer.array(), 0, buffer.remaining()));
        }
        //6. 多个缓冲区数据聚合到通道
        osChannel.write(buffers); //自动合并缓冲区数组中的数据到通道
        isChannel.close();
        osChannel.close();
        System.out.println("Success to copy!");

    }

    @Test
    public void copy() {
        //源文件
        File srcFile = new File("data01.txt");
        try {

            //源文件输入流和输入通道
            FileInputStream fis = new FileInputStream(srcFile);
            FileChannel fisChannel = fis.getChannel();

            //目标文件输出流和输出通道
            FileOutputStream fos = new FileOutputStream("data01_copy.txt");
            FileChannel fosChannel = fos.getChannel();

            //分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //读入缓冲区
            fisChannel.read(buffer);
            buffer.flip();

            //写入文件, 文件可能很大，需要循环拷贝
            while (true){
                //清空缓冲区，然后再读入数据
                buffer.clear();
                int flag = fisChannel.read(buffer);
                if (flag == -1) {
                    break;
                }

                buffer.flip();//切换读模式
                fosChannel.write(buffer);
            }

            fisChannel.close();
            fosChannel.close();
            System.out.println("拷贝文件成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void write() {
        //1. 字节输出流同向目标文件
        try {
//            FileOutputStream fos = new FileOutputStream("src\\main\\resources\\data01.txt");
            FileOutputStream fos = new FileOutputStream("data01.txt");//默认文件路径是当前项目的路径

            //2. 得到字节输出流对应的通道channel,
            // 如果是使用OutputStream那么没有channel
            FileChannel channel = fos.getChannel();

            //3. 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put(("hello,黑马Java程序员！").getBytes());

            //4. 把缓冲区切换成读模式
            buffer.flip();
            channel.write(buffer);
            System.out.println("成功写入文件！");

            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read(){

        try {
            //1. 定义一个文件输入流与文件接通
            FileInputStream fis = new FileInputStream("data01.txt");

            //2. 得到与文件字节输入流的文件通道
            FileChannel channel = fis.getChannel();

            //3. 定义缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //4. 读取数据到缓冲区
            channel.read(buffer);
            String rs = new String(buffer.array());
            System.out.println(rs.length());//
            System.out.println(rs);

            System.out.println("-------------------");

            //使用下面的方法读取
            channel.read(buffer);
            buffer.flip();
            String rs2 = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(rs2.length());
            System.out.println(rs2);


            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
