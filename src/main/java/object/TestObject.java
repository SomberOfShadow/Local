package object;

public class TestObject {
    public static void main(String[] args) {
//        ObjectTest objectTest = new ObjectTest(null, null);
//        System.out.println("object test " + objectTest);
//
//        objectTest.getDate();
//        System.out.println("date is " + objectTest.getDate());

        ObjectTest objectTest = ObjectTest.getInstance();

        ObjectTest objectTest1 = ObjectTest.getInstance();
        objectTest1.setDate("date");
        objectTest1.setName("name");
        objectTest1.setAge(10);

        System.out.println(objectTest.getAge());
    }
}
