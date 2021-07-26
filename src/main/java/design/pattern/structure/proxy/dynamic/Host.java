package design.pattern.structure.proxy.dynamic;

public class Host implements Rent {
    @Override
    public void rent(String name) {
        System.out.println(name + "房主出租房子！");
    }
}
