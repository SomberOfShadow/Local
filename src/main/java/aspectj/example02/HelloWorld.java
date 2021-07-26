package aspectj.example02;


public class HelloWorld implements HelloWorldInterface {

    @Override
    public void sayHello() {
        System.out.println("Hello, World!");
    }

}
