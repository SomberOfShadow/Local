package exam.deke;

import java.util.Scanner;

/**
 * @Classname Demo2
 * @Description TODO
 * @Date 2021-09-07 9:32 PM
 * @Created by eenheni
 *
 * 输出字符串，和区间，根据指定区间反转字符串
 * Input:
 * I am a  developer.
 * 0
 * 3
 *
 * Output:
 * developer. a am I
 *
 * Input:
 * Hello!
 * 0
 * 3
 *
 * Outout:
 * EMPTY(如果只有一个单词，那么输出EMPTY)
 *
 * Input:
 * Hello World!
 * 0
 * 1
 *
 * Output:
 * World! Hello
 *
 *
 */
public class Demo2 {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String sentence = scanner.nextLine();

//        System.out.println(sentence);

        int start = scanner.nextInt();
        int end = scanner.nextInt();
        reverseWords(sentence, start, end);
    }

    private static void reverseWords(String sentence, int start, int end) {
        String[] words = sentence.trim().split(" ");
        int length = words.length;
        String[] newWords = new String[length];
        int j = 0;
        for (String  word : words) {
            if (word.trim().length() != 0) {
                newWords[j] = word;
                j++;
            }
        }
//        System.out.println("j: " + j);
        int newLength = j;

        if (newLength > 1){
            reversePrint2(newWords, 0, end, newLength-1);
        } else {
            System.out.println("EMPTY");
        }
    }

    private static void reversePrint2(String[] strings, int start, int end, int endPosition) {
        StringBuilder stringBuilder = new StringBuilder();

        if (start > 0) {
            for (int i = 0; i < start ; i++) {
                stringBuilder.append(strings[i]).append(" ");
            }
        }

        if (end < endPosition) {
            for (int j = end; j >= start; j--) {
                if (j == start) {
                    stringBuilder.append(strings[j]);
                } else {
                    stringBuilder.append(strings[j]).append(" ");
                }
            }
            for (int k = end + 1; k <= endPosition ; k++) {
                if (k == endPosition) {
                    stringBuilder.append(strings[k]);
                } else {
                    stringBuilder.append(strings[k]).append(" ");
                }

            }
        }

        if (end >= endPosition) {
            for (int j = end; j >= start; j--) {
                stringBuilder.append(strings[j]).append(" ");
            }
        }
        System.out.println(stringBuilder.toString());
    }

}
