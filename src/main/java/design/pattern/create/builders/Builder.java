package design.pattern.create.builders;

/**
 *  @Description: Builder 建造者模式
 *  Builder也可以是实现某个接口

 */
public class Builder {
    private String name = "default";
    private int id = 0;
    private String sex = "default";

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getSex() {
        return sex;
    }

    public Builder setName(String name) {
        this.name = name;
        return this;
    }

    public Builder setId(int id) {
        this.id = id;
        return this;
    }

    public Builder setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public Creator build() {
        return new Creator(this);
    }

}
