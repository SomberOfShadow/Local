package leetcode.thread;


/**
 *  @Description: ZeroEvenOdd
 *  @ClassName: ZeroEvenOdd
 *
 *  输出0奇数偶数
 *  示例 1：
 *
 * 输入：n = 2
 * 输出："0102"
 * 说明：三条线程异步执行，其中一个调用 zero()，另一个线程调用 even()，最后一个线程调用odd()。正确的输出为 "0102"。
 * 示例 2：
 *
 * 输入：n = 5
 * 输出："0102030405"
 */

public class ZeroEvenOdd {
    private int n;
    private int index = 1;
    int number = 1;


    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    public void zero(int printNumber) throws InterruptedException {
        for (int i = 0; i < printNumber; i++) {
            synchronized (this){
                while (index % 2 == 0){
                    this.wait();
                }
                System.out.print(0);
                index++;
                this.notifyAll();
            }
        }
    }

    //打印奇数
    public void odd(int printNumber) throws InterruptedException {

        for (int i = 1; i <= printNumber; i += 2) {
            synchronized (this){
                while ((index % 2 == 1) || (index % 4 == 0)){
                    this.wait();
                }
                System.out.print(number);
                index++;
                number++;
                this.notifyAll();
            }
        }
    }


    //打印偶数
    public void even(int printNumber) throws InterruptedException {

        for (int i = 1; i <= printNumber; i += 2) {
            synchronized (this){
                while (index % 4 != 0){
                    this.wait();
                }
                System.out.print(number);
                index++;
                number++;
                this.notifyAll();
            }
        }
    }

}
