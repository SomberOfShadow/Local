package design.pattern.create.builders;

public class Test {
    public static void main(String[] args) {

        Builder builders = new Builder();

        Creator creator = builders.setName("name")
                .setId(1)
                .setSex("male")
                .build();

        System.out.println(creator.toString());

        // get current method name
        System.out.println(new Throwable().getStackTrace()[0].getMethodName());
        System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName());

        test01();
    }

    public static void test() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();

        for(int i=0;i<stack.length;i++){
            System.out.println(stack[i].getClassName()+"."+stack[i].getMethodName()+"-----");
        }
    }

    public static void test01() {
        test();
    }

}
