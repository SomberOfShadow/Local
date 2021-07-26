package design.pattern.create.factory.abstractf;

public class Client {
    public static void main(String[] args) {

        System.out.println("----------xiao mi-------------");
        XiaomiFactory xiaomiFactory = new XiaomiFactory();
        IphoneProduct iphoneProduct = xiaomiFactory.iphoneProduct();

        iphoneProduct.callup();
        iphoneProduct.sendMess();
        RouteProduct routeProduct = xiaomiFactory.routeProduct();
        routeProduct.openwifi();
        routeProduct.setting();

        System.out.println("----------hua wei-------------");

        HuaweiFactory huaweiFactory = new HuaweiFactory();
        IphoneProduct iphoneProduct1 = huaweiFactory.iphoneProduct();
        iphoneProduct1.sendMess();;
        iphoneProduct1.shutdown();
        RouteProduct routeProduct1 = huaweiFactory.routeProduct();
        routeProduct1.setting();
        routeProduct1.openwifi();
    }
}
