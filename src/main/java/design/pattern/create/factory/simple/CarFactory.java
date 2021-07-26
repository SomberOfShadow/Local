package design.pattern.create.factory.simple;

/**
 *简单工厂模式（静态工厂模式）
 *
 */
public class CarFactory {

    public static Car getCar (String car) {
        if(car.equals("wuling")) {
            return new Wuling();
        }else if (car.equals("tesla")){
            return new Tesla();
        } else {
            return null;
        }

    }
}
