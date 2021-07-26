package design.pattern.structure.bridge;

public class Apple implements Brand {
    @Override
    public void info() {
        System.out.println("Apple");
    }
}
