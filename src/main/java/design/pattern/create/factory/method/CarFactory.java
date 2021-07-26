package design.pattern.create.factory.method;


/**
 * 工厂方法模式，将拿车的方法抽象成一个接口，因为如果要增加车型，原来简单工厂模式中，
 *  工厂需要改变代码
 *  本质：Car是一个接口， CarFactory是一个接口
 */
public interface CarFactory {
    Car getCar();
}
