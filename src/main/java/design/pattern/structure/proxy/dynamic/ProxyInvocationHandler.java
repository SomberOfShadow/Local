package design.pattern.structure.proxy.dynamic;

import java.lang.reflect.*;


/**
 *  @Description: ProxyInvocationHandler
 *  @ClassName: ProxyInvocationHandler
 *  这是一个common的类, 可以直接在项目中使用
 */
public class ProxyInvocationHandler implements InvocationHandler {

    //被代理的接口
    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    //生成得到代理类
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                this);

    }

    //处理代理实例，返回处理结果
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            System.out.println(parameter.getType());
        }

        fare(method.getName());
        seeHouse(method.getName());


        Object result = method.invoke(target, args);
        return result;
    }


    //增加以下额外的操作
    private void fare(String name) {
        System.out.println("收取" + name +  " 的中介费！");
    }

    private void seeHouse(String name){
        System.out.println("中介带看" + name + " 房子！");
    }
}
