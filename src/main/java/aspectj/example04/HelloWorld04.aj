public aspect HelloWorld04 {

    pointcut addPointcut():execution(* aspectj.example04.HelloWorld04.sayHello(..));

    before() : addPointcut() {
         System.out.println("Hello, AspectJ!");
    }

}