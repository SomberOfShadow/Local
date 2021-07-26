package design.pattern.create.prototype;

import java.util.Date;


/**
 *  @Description: Vedio
 *  原型模式要实现Cloneable接口
 *  默认调用super.clone(),是浅拷贝
 *  如果想要使用深烤贝,需要把每个属性也进行烤贝

 */
public class Vedio implements Cloneable{

    private String name;
    private Date createdTime;

    public Vedio() {
    }

    public Vedio(String name, Date createdTime) {
        this.name = name;
        this.createdTime = createdTime;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        Vedio vedio = (Vedio) super.clone();

        // 深拷贝需要把属性也进行拷贝
        vedio.name = this.name;
        vedio.createdTime = (Date) this.createdTime.clone();
        return vedio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Vedio{" +
                "name='" + name + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }
}
