package leetcode.thread;

import java.util.Scanner;

public class TestZeroEvenOdd {
    public static void main(String[] args) {

        // 从键盘输入
//        Scanner scanner = new Scanner(System.in);
//        int testNumber = scanner.nextInt();


        int testNumber = 5;
        ZeroEvenOdd zeroEvenOdd = new ZeroEvenOdd(testNumber);

        new Thread(()->{
            try {
                zeroEvenOdd.zero(testNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-A").start();

        new Thread(()->{
            try {
                zeroEvenOdd.odd(testNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-B").start();

        new Thread(()->{
            try {
                zeroEvenOdd.even(testNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread-C").start();
    }
}
