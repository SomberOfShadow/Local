package design.pattern.create.factory.abstractf;

/**
 *  工厂的接口，用于生产手机和路由器
 *
 */
public interface ProductFactory {
    IphoneProduct iphoneProduct();
    RouteProduct routeProduct();
}
