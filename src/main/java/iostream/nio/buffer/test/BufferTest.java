package iostream.nio.buffer.test;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * @Classname BufferTest
 * @Description
 * 目标：
 *  对缓冲区Buffer的常用API进行案例实现
 *
 * @Date 2021-07-15 3:56 PM
 * @Created by eenheni
 */
public class BufferTest {
  @Test
    public void test01(){
    //1. 分配缓冲区并设置容量
    ByteBuffer buffer = ByteBuffer.allocate(10);

    System.out.println(buffer.position());//0
    System.out.println(buffer.limit());//10
    System.out.println(buffer.capacity());//10

    System.out.println("-------------------------");

    //2. put API add data
    String name = "heima";
    buffer.put(name.getBytes());
    System.out.println(buffer.position());//5
    System.out.println(buffer.limit());//10
    System.out.println(buffer.capacity());//10

    System.out.println("-------------------------");

    //3. 将Buffer flip() 为缓冲区的界限设置为当前值，并将当前位置设置为0:可读模式
    buffer.flip();
    System.out.println(buffer.position());//0
    System.out.println(buffer.limit());//5
    System.out.println(buffer.capacity());//10
    System.out.println("-------------------------");
    //4. get数据
    byte b = buffer.get();//默认是取当前的一个字节
    char c = (char)b;
    System.out.println(c);
    System.out.println(buffer.position());//1
    System.out.println(buffer.limit());//5
    System.out.println(buffer.capacity());//10
    System.out.println("-------------------------");
  }


  @Test
  public void test02() {
    //1. 分配缓冲区并设置容量
    ByteBuffer buffer = ByteBuffer.allocate(10);

    System.out.println(buffer.position());//0
    System.out.println(buffer.limit());//10
    System.out.println(buffer.capacity());//10

    System.out.println("-------------------------");
    String name = "heima";
    buffer.put(name.getBytes());
    System.out.println(buffer.position());//5
    System.out.println(buffer.limit());//10
    System.out.println(buffer.capacity());//10
    //2. clear
    buffer.clear();

    System.out.println(buffer.position());//0
    System.out.println(buffer.limit());//10
    System.out.println(buffer.capacity());//10
    System.out.println((char)buffer.get());//h, clear 之后依然输出h
    System.out.println("-------------------------");

    //3.
    ByteBuffer buf = ByteBuffer.allocate(10);
    String n = "itheima";
    buf.put(n.getBytes());
    buf.flip();

    // 读取数据,定义读取两个字节
    byte[] bytes = new byte[2];

    buf.get(bytes);
    String s = new String(bytes);
    System.out.println(s);//it

    System.out.println(buf.position());//2
    System.out.println(buf.limit());//7
    System.out.println(buf.capacity());//10

    System.out.println("-------------------------");
    buf.mark();
    byte[] b2 = new byte[3];
    buf.get(b2);
    System.out.println(new String(b2));//hei

    System.out.println(buf.position());//5
    System.out.println(buf.limit());//7
    System.out.println(buf.capacity());//10
    System.out.println("-------------------------");
    buf.reset();
    if (buf.hasRemaining()) {
      System.out.println(buf.remaining());//5
    }
  }

  @Test
  public void test03(){
    // 申请直接内存
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
    System.out.println(byteBuffer.isDirect());//true

  }
}
