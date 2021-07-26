package design.pattern.create.factory.simple;

public class Test {
    public static void main(String[] args) {

        // 传统模式
        Wuling wuling1 = new Wuling();
        Tesla tesla1 = new Tesla();


        //简单工厂模式，不使用new 创建对象，使用工厂获取对象
        Car wuling = CarFactory.getCar("wuling");
        wuling.name();
        Car tesla = CarFactory.getCar("tesla");
        tesla.name();


    }
}
