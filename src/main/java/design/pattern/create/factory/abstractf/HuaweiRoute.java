package design.pattern.create.factory.abstractf;

public class HuaweiRoute implements RouteProduct{
    @Override
    public void start() {
        System.out.println("开启华为路由器");
    }

    @Override
    public void shutdown() {
        System.out.println("关闭华为路由器");
    }

    @Override
    public void openwifi() {
        System.out.println("开启华为路由器wifi");
    }

    @Override
    public void setting() {
        System.out.println("华为路由器配置");
    }
}
