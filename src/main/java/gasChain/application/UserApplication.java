package gasChain.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Console App Test...");
	}

}
