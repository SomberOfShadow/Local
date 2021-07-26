package design.pattern.create.prototype;

import java.util.Date;

public class Test {
    public static void main(String[] args) throws CloneNotSupportedException {
        // 原型
        Date date = new Date();
        Vedio vedio = new Vedio("prototype", date);
        System.out.println(vedio);

        // 拷贝
        Object v2 = vedio.clone();
        System.out.println(v2);

        // 改变date对象的值或者name的值,如过是浅拷贝，那么原型和拷贝后的对象都会改变
        // 如果是深拷贝，那么拷贝后的值是不会被影响的
        date.setTime(121212121);
        vedio.setName("test");

        System.out.println(vedio);
        System.out.println(v2);

        /**
         * Output:
         Vedio{name='prototype', createdTime=Thu Jul 01 14:40:05 CST 2021}
         Vedio{name='prototype', createdTime=Thu Jul 01 14:40:05 CST 2021}
         Vedio{name='test', createdTime=Fri Jan 02 17:40:12 CST 1970}
         Vedio{name='prototype', createdTime=Thu Jul 01 14:40:05 CST 2021}
         */

    }
}
