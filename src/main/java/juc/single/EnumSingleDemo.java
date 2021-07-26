package juc.single;

/**
 *  @Description: EnumSingleDemo
 *  使用枚举的方式实现单例模式，而且反射是没办法破坏枚举的单例的，
 *  其他单例实现方式都可以被反射所破坏
 */
public enum EnumSingleDemo {

    INSTANCE(1, "name");

    private int id;
    private String name;

    EnumSingleDemo(int id, String name){
        this.id = id;
        this.name = name;
    }
    public static EnumSingleDemo getInstance() {
        return INSTANCE;
    }
}
