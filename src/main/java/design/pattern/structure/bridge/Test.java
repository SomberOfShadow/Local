package design.pattern.structure.bridge;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  @Description: Test
 *  @ClassName: Test
 *  桥接模式：
 *  多维度的拆分
 *  一个维度是品牌
 *  一个维度是电脑
 *
 *  然后通过组合的方式，在电脑的抽象类中自带品牌
 *  桥接模式由于多继承
 *
 *  经典应用：JDBC驱动程序
 */

public class Test {
    public static void main(String[] args) {
        //苹果笔记本
        Computer laptop = new Laptop(new Apple());
        laptop.info();

        //联想台式机
        Desktop desktop = new Desktop(new Lenovo());
        desktop.info();


        Executors.newCachedThreadPool();
    }
}
