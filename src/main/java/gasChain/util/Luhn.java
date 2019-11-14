package gasChain.util;

import java.util.Random;

public class Luhn {

    public static boolean validate(String cardNumber) {

        int length = cardNumber.length();
        int[] cnArray = new int[length];
        int index = 0;
        for (char c : cardNumber.toCharArray()) {
            int value = Character.getNumericValue(c);
            if (value <= 9 && value >= 0) {
                cnArray[index++] = value;
            } else {
                return false;
            }
        }
        int total = 0;
        for (int i = length - 2; i >= 0; i--) {
            if ((length - 2 - i) % 2 == 0) {
                int workingNum = 2 * cnArray[i];
                total += workingNum > 9 ? workingNum % 10 + 1 : workingNum;
            } else {
                total += cnArray[i];
            }
        }
        return (total + cnArray[length - 1]) % 10 == 0;
    }

    public static String generateLuhn(int num_digits) {
        int total = 0;
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < num_digits; i++) {
            int val = r.nextInt(10);
            if (i % 2 == 1) {
                total += val * 2 > 9 ? (val * 2) % 10 + 1 : val * 2;
            } else {
                total += val;
            }
            sb.append(val);
        }
        sb.reverse();
        sb.append(total % 10 != 0 ? (10 - total % 10) : 0);
        return sb.toString();
    }
}

