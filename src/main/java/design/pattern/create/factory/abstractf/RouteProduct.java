package design.pattern.create.factory.abstractf;

/**
 *  @Description: RouteProduct 路由器产品接口
 *
 */
public interface RouteProduct {
    void start();
    void shutdown();
    void openwifi();
    void setting();
}
