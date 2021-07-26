package thread.examples.race;


/**
 *  @Description: 模拟龟兔赛跑
 *  @ClassName: Race
 */
public class Race implements Runnable {

    public static String winner;
    @Override
    public void run() {
        // 模拟兔子睡觉
//        if (Thread.currentThread().getName().contains("Rabbit")) {
//            try {
//                Thread.sleep(0);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        for (int i = 0; i <= 100; i++) {

            boolean flag = gameOver(i);
            if (!flag) {
                System.out.println(Thread.currentThread().getName() + "--> 跑了" + i + "米");
            }
        }

    }

    public boolean gameOver(int distance) {

        boolean over = false;
        if (winner != null) {
            over = true;
        } else {
            if (distance >= 100){
                winner = Thread.currentThread().getName();
                System.out.println("Winner is " + winner);
                over = true;
            }
        }
        return over;
    }

    public static void main(String[] args) {
        Race race = new Race();

        new Thread(race, "Turgle").start();
        new Thread(race, "Rabbit").start();

    }

}

