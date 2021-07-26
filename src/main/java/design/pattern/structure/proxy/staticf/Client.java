package design.pattern.structure.proxy.staticf;


/**
 *  @Description: Client
 *  @ClassName: Client
 *
 *  模拟租客租房子
 *  中介和房主有一个共同的接口，中介实现接口，同时会有一些附属的操作
 *  租客直接对接中介，中介负责出租房主的房子
 *
 */
public class Client {

    public static void main(String[] args) {

        Host host = new Host();
        Proxy proxy = new Proxy(host);

        proxy.rent();

    }
}
