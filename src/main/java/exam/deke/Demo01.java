package exam.deke;

import java.util.Scanner;


/**
 * @Classname Demo01
 * @Description TODO
 * @Date 2021-09-07 8:57 PM
 * @Created by eenheni
 *
 * input:
 * 3
 * 0
 * output:
 * 153
 *
 * input:
 * 9
 * 1
 * output:
 * -1
 */
public class Demo01 {
    private static int count = -1;
    public static void main(String[] args) {


        String s  = "  ";
        System.out.println(s.trim().length());
        System.exit(-1);
        Scanner scanner = new Scanner(System.in);

        int N = scanner.nextInt();//first input
        int M = scanner.nextInt();//second input
//        System.out.println(N);
//        System.out.println(M);
        int[] ints = new int[100];
        if (N >= 3 && N <= 7){
            int start = (int)Math.pow(10, N-1);
//            System.out.println(start);
            int end = (int)Math.pow(10, N) - 1;
//            System.out.println(end);
            int i = start;
            for (i = start; i <= end ; i++) {
                if (isShuiHuaXianNumber(i)) {
                    ints[++count] = i;
                }
            }

            if (M > count) {
                System.out.println(ints[count] * M);
            } else {
                System.out.println(ints[M]);
            }
        } else {
            System.out.println(-1);
        }
        scanner.close();
    }


    /**
     *  Judge whether a number is a shui xian hua shu
     *
     * @param number target number
     * @return boolean
     */

    private static boolean isShuiHuaXianNumber(int number) {


        int sum = 0;
        int replication = number;
        while (number > 0) {
            int temp = number % 10;
            number = number / 10;
            sum += (int)Math.pow(temp, 3);
        }
        if (sum == replication) {
         return true;
        }
        return false;
    }

}
