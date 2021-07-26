package aspectj.example01;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 *  @Description: HelloWorldAspectJ
 *  @ClassName: HelloWorldAspectJ
 *  使用注解的方式和使用.aj的方式都可以
 *
 *  使用aspectj maven插件进行编译(mvn clean compile/install... -Dmaven.test.skip=true -Dmaven.javadoc.skip=true)
 *  另外引入aspectjrt的dependency
 *
 *  可以对类进行编织也可以对接口进行
 */
@Aspect
public class HelloWorldAspectJ {


    //使用execution，则代码编织在业务类中(HelloWorld),如果使用的是call则编织在测试中
    //如果使用了call，但是没有测试类，那么会提示一个not applied的warning
    @Pointcut("execution(* aspectj.example01.HelloWorldInterface.sayHello(..))")
    public void addPointcut() {
    }

    @Before("addPointcut()")
    public void addAdvice(JoinPoint joinPoint) {
        System.out.println();
        System.out.println("Hello, AspectJ!");
        System.out.println("joinPoint: " + joinPoint);
        System.out.println("Source Line: " + joinPoint.getSourceLocation());
    }

}
