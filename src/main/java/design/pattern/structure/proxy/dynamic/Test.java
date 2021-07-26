package design.pattern.structure.proxy.dynamic;

/**
 *  @Description: Test
 *  @ClassName: Test
 *
 *  动态代理分为两大类：
 *  基于接口的动态代理和基于类的动态代理
 *
 *  基于接口：----JDK的动态的代理
 *  基于类：-----cglib
 *  Java字节码的实现：javasist
 *
 *  Proxy:代理
 *  InvocationHandler: 调用处理程序
 */
public class Test {

    public static void main(String[] args) {

        //真是角色-被代理对象
        Host host = new Host();

        ProxyInvocationHandler proxyInvocationHandler = new ProxyInvocationHandler();
        proxyInvocationHandler.setTarget(host);

        //动态生成代理类
        Rent proxy = (Rent) proxyInvocationHandler.getProxy();

        proxy.rent("fangzhu");

    }
}
