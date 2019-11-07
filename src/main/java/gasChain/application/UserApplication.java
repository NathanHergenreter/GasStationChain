package gasChain.application;

import gasChain.entity.Employee;
import gasChain.scanner.MethodScanner;
import gasChain.service.ServiceMaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {


	static Scanner in;
	@Autowired
	ServiceMaster service;

	@Override
	public void run(String... args) {

		in = new Scanner(System.in);
		boolean exit = false;
		Employee employee = user_login(in);
		System.out.println("Enter 'exit' to close application");
		String input;
		while (!exit) {
			System.out.println("Enter Command: ");
			input = in.nextLine();
			if (input.equals("/help")) {
				MethodScanner.printCommands(employee);
			} else if (input.equals("exit")) {
				exit = true;
			} else {
				List<String> result = Arrays.asList(input.split(";"));
				for (String s : result) {
					System.out.println("Results: " + s);
				}
				MethodScanner.runEmployeeCommand(result, employee);
			}
		}
	}


	private Employee user_login(Scanner in) {
		Employee employee = null;
		while (employee == null) {
			System.out.println("Enter username: ");
			String username = in.nextLine();

			System.out.println("Enter password: ");
			String password = in.nextLine();

			employee = validateUser(username, password);
			if (employee == null) {
				System.out.println();
				System.out.println("Invalid username or password");
				System.out.println();

			}
		}
		return employee;
	}

	private Employee validateUser(String username, String password) {
		Employee employee = service.cashier().findByUsername(username);
		employee = employee == null ? service.manager().findByUsername(username) : employee;
		employee = employee == null ? service.corporate().findByUsername(username) : employee;

		return (employee != null && employee.getPassword().equals(password)) ? employee : null;
	}
}
