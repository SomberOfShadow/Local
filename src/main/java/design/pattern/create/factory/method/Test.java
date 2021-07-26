package design.pattern.create.factory.method;


public class Test {
    public static void main(String[] args) {
        //工厂方法模式，有多个工厂
        Car wuling = new WulingFactory().getCar();
        wuling.name();

        Car tesla = new TeslaFactory().getCar();
        tesla.name();

        // 增加一个膜拜单车的实现类和工厂
        Car mobai = new MobaiFactory().getCar();
        mobai.name();

    }
}
