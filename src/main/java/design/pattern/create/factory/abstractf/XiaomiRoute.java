package design.pattern.create.factory.abstractf;

public class XiaomiRoute implements RouteProduct {
    @Override
    public void start() {
        System.out.println("开启小米路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭小米路由器");
    }

    @Override
    public void openwifi() {
        System.out.println("开启小米路由器wifi");
    }

    @Override
    public void setting() {
        System.out.println("小米路由器配置");
    }
}
