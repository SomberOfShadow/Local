package object;

public class ObjectTest {
    String date;

    String name;

    int age;

    private static ObjectTest instance = null;

    public static ObjectTest getInstance(){

        if (instance == null) {
            instance = new ObjectTest();
        }
        return instance;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public ObjectTest() {
    }

    public ObjectTest(String date, String name) {
        this.date = date;
        this.name = name;
    }


    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }
}
