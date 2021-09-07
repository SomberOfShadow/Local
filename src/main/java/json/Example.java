package json;

/**
 * @Classname Example
 * @Description TODO
 * @Date 2021-08-06 10:20 AM
 * @Created by eenheni
 */
class Example{
    String name;
    int id;

    public Example() {
    }

    public Example(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Example setName(String name) {
        this.name = name;
        return this;
    }

    public Example setId(int id) {
        this.id = id;
        return this;
    }
}
