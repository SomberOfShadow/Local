package design.pattern.create.factory.abstractf;

public class XiaomiFactory implements ProductFactory {
    @Override
    public IphoneProduct iphoneProduct() {
        return new XiaomiPhone();
    }

    @Override
    public RouteProduct routeProduct() {
        return new XiaomiRoute();
    }
}
