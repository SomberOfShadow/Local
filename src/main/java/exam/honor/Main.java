package exam.honor;

import java.util.Scanner;

/**
 * @Classname Main
 * @Date 2021-09-02 10:07 PM
 * @Created by eenheni
 */
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        int sumn = sum(n);
        int summ = sum(m);

        System.out.println(sumn%summ);
//        System.out.println(sum(scanner.nextInt())%sum(scanner.nextInt()));
    }

    /**
     *  sum all numbers included in the number
     *
     *  Example:
     *  input : 123
     *  output: 6 = 1 + 2 + 3
     *
     * @param k target number
     * @return int
     */
    private static int sum(int k) {

        int sumk = 0;
        while (k > 0) {
            int temp = k % 10;
            k = k / 10;
            sumk += temp;
        }
        return sumk;
    }

}
