package design.pattern.create.factory.method;

public class MobaiFactory implements CarFactory {
    @Override
    public Car getCar() {
        return new Mobai();
    }
}
