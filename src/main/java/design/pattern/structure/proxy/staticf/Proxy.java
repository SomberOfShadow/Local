package design.pattern.structure.proxy.staticf;

public class Proxy implements Rent{
    private Host host;

    public Proxy(Host host) {
        this.host = host;
    }

    @Override
    public void rent() {
        host.rent();
        contract();
    }


    //附属操作
    public void contract() {
        System.out.println("签合同");
    }
}
