package gasChain.generator;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class GenUtil {

	protected static Random rng = new Random();

	// Produces a random date from the past x years
	public static Date genDate() {
		int yearRange = 5;
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
}
