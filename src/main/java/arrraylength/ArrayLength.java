package arrraylength;

public class ArrayLength {
    public static void main(String[] args) {
        int[] a = new int[10];
        int[] b = new int[20];

        int length = a.length < b.length ? a.length : b.length;

        System.out.println(length);
        for (int i = 0; i < length; i++ ) {
            //
        }
    }
}
