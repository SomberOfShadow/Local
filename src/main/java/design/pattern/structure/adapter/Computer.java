package design.pattern.structure.adapter;

/**
 *  @Description: Computer
 *  客户端类，想上网，插不上网线
 *  @ClassName: Computer
 */
public class Computer {
    public void net (NetToUsb adapter) {
        // 上网的具体实现类，找一个转接头
        adapter.handleRequest();

    }

    public static void main(String[] args) {
        //电脑 网线 适配器
        Computer computer = new Computer();
        Adaptee adaptee = new Adaptee();//貌似网线没用，其实是适配器继承了网线，类似于集成网卡
        Adapter adapter = new Adapter();

        computer.net(adapter);
        System.out.println("=====================");

        //电脑 网线 适配器

        Computer computer2 = new Computer();
        Adaptee adaptee2 = new Adaptee();
        // 电脑连接适配器，适配器连接网线
        Adapter2 adapter2 = new Adapter2(adaptee2);
        computer2.net(adapter2);
    }
}
