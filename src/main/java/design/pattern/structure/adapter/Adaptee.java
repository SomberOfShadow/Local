package design.pattern.structure.adapter;

/**
 *  @Description: Adaptee 要被适配的类：网线
 *  @ClassName: Adaptee
 */
public class Adaptee {
    public void request() {
        System.out.println("成功连网！");
    }
}
