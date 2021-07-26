public aspect HelloWorld05 {

    pointcut addPointcut():execution(* aspectj.example05.HelloWorldImpl.sayHelloImpl(..));

    before() : addPointcut() {
         System.out.println("Hello, AspectJ!");
    }

}