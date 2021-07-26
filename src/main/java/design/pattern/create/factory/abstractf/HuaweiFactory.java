package design.pattern.create.factory.abstractf;

public class HuaweiFactory implements ProductFactory {
    @Override
    public IphoneProduct iphoneProduct() {
        return new HuaweiPhone();
    }

    @Override
    public RouteProduct routeProduct() {
        return new HuaweiRoute();
    }
}
