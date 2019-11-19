package gasChain.generator;

import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

@Component
public class GenUtil {

    protected static Random rng = new Random();

    // Produces a random date from the past x years
    public static Date genDate() {
        int yearRange = 1;
        Random rng = new Random();
        String year = Integer.toString(2019 - rng.nextInt(yearRange));
        String month = Integer.toString((rng.nextInt(12) + 1));
        String day = Integer.toString((rng.nextInt(28) + 1));

        String sdf = year + "-" + month + "-" + day;

        return Date.valueOf(sdf);
    }

    public static String genRandomName(ArrayList<String> firstNames, ArrayList<String> lastNames) {
        return firstNames.get(rng.nextInt(firstNames.size())) + " " + lastNames.get(rng.nextInt(lastNames.size()));
    }

    public static String genRandonPhoneNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(rng.nextInt(10));
        }
        return sb.toString();
    }

    public static String genRandomEmail() {
        StringBuilder sb = new StringBuilder();
        int numChar = rng.nextInt(19) + 6;
        for (int i = 0; i < numChar; i++) {
            sb.append((char) (rng.nextInt(26) + 97));
        }
        sb.append("@gmail.com");
        return sb.toString();
    }
}
