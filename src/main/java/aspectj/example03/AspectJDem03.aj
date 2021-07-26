aspect AspectDemo03 {

    pointcut addPointcut():execution(* aspectj.example03.HelloWorld03.sayHello(..));

    before() : addPointcut() {
         System.out.println("Hello, AspectJ!");
    }

}