package design.pattern.create.builders;

public class Creator {
    private String name;
    private int id;
    private String sex;

    public Creator(){

    }
    public Creator(Builder builder) {
        name = builder.getName();
        id = builder.getId();
        sex = builder.getSex();
    }


    @Override
    public String toString() {
        return "Creator{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", sex='" + sex + '\'' +
                '}';
    }
}
