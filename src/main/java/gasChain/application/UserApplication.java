package gasChain.application;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import gasChain.entity.GasStation;
import gasChain.service.GasStationService;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Console App Test...");
	}

}
