package design.pattern.structure.adapter;

/**
 *  @Description: Adapter
 *  真正的适配器，需要连接电脑和网线
 *  @ClassName: Adapter
 *
 *  1. 继承（类适配器，单继承）
 *  2. 组合（对象适配器，常用）
 */
public class Adapter extends Adaptee implements NetToUsb{
    @Override
    public void handleRequest() {
       super.request();
    }
}
