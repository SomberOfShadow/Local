package exam.honor;

import java.util.Scanner;

/**
 * @Classname Main2
 * @Date 2021-09-02 10:29 PM
 * @Created by eenheni
 *
 * Example input:
 * 6 8
 * 1 2 3 4 5 6
 * Q 1 6
 * U 2 6
 * U 4 3
 * Q 2 4
 * Q 1 2
 * U 1 3
 * U 2 1
 * Q 1 3
 * Example output:
 *  3
 *  6
 *  4
 *  5
 */
public class Main2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int M = scanner.nextInt();
        int[] intN = new int[N];
        int i = 0;
        while (N > 0) {
            intN[i] = scanner.nextInt();
            i++;
            N--;
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (M > 0) {
            String s = scanner.nextLine();
            String operation = s.split(" ")[0];
            if (operation.equalsIgnoreCase("Q")) {
                int position = Integer.parseInt(s.split(" ")[1]) - 1;
                int number = Integer.parseInt(s.split(" ")[2]) - 1;
                int sum = 0;
                for(int j = position; j <= number; j++) {
                    sum += intN[j];
                }
                int average = sum/(number - position + 1);
                stringBuilder.append(average).append("\n");
                }else if (operation.equalsIgnoreCase("U")) {
                int position = Integer.parseInt(s.split(" ")[1]) - 1;
                int number = Integer.parseInt(s.split(" ")[2]);
                intN[position] += number;
            }
            M--;
        }
        System.out.println(stringBuilder.toString());
    }
}
