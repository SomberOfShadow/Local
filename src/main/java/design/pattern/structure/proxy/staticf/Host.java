package design.pattern.structure.proxy.staticf;

public class Host implements Rent {
    @Override
    public void rent() {
        System.out.println("房主出租房子！");
    }
}
