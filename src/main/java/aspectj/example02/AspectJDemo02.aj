aspect AspectDemo02 {

    pointcut addPointcut():call(* aspectj.example02.HelloWorldInterface.sayHello(..));

    before() : addPointcut() {
         System.out.println("Hello, AspectJ!");
    }

}