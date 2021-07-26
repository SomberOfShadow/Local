package aspectj.example02;

public class HelloWorldTest {
    public static void main(String[] args) {
        HelloWorld helloWorld = new HelloWorld();
        helloWorld.sayHello();

        System.out.println("================================================");
        //HelloWorld2
        HelloWorld2 helloWorld2 = new HelloWorld2();
        helloWorld2.sayHello();
    }
}
