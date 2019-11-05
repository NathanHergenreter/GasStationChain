package gasChain.util;

public class Luhn {

    public static boolean validate(String cardNumber) {

        int length = cardNumber.length();
        int[] cnArray = new int[length];
        int index = length;
        for (char c : cardNumber.toCharArray()) {
            int value = Character.getNumericValue(c);
            if (value <= 9 && value >= 0) {
                cnArray[--index] = value;
            } else {
                return false;
            }
        }
        return true;
//        int total = 0;
//        for (int i = 0; i < length; i++) {
//            if (i % 2 == 0) {
//                int workingNum = 2 * cnArray[i];
//                total += workingNum > 9 ? workingNum % 10 + 1 : workingNum;
//            } else {
//                total += cnArray[i];
//            }
//        }
//        return total % 10 == 0;
//
//    }
    }
}

