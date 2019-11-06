package gasChain.application;

import gasChain.application.manager.interfaces.IUserHelper;
import gasChain.application.controller.interfaces.IUserController;
import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.Employee;
import gasChain.entity.Manager;
import gasChain.application.manager.CashierHelper;
import gasChain.application.manager.CorporateHelper;
import gasChain.application.manager.ManagerHelper;
import gasChain.util.ServiceMaster;
import gasChain.application.controller.CashierController;
import gasChain.application.controller.CorporateController;
import gasChain.application.controller.ManagerController;
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

	@Autowired
	ServiceMaster service;

	static Scanner in;

	IUserController _controller;
	
	@Override
	public void run(String... args) throws Exception {
		in = new Scanner(System.in);
		boolean exit = false;

		System.out.println();
		while(!exit)
		{
			System.out.println("Enter username: ");
			String username = in.nextLine();
			
			System.out.println("Enter password: ");
			String password = in.nextLine();
			
			IUserHelper helper;
	
			Employee employee = validateUser(username, password);
			String auth = employee != null ? employee.getAuth() : "INVALID";
			
			switch(auth)
			{
				case "cashier":
					helper = CashierHelper.cashierHelper((Cashier) employee);
	                _controller = new CashierController((CashierHelper)helper);
	                promptUser(in);
					break;
				case "manager":
				    helper = new ManagerHelper((Manager) employee);
				    _controller = new ManagerController((ManagerHelper)helper);
	                promptUser(in);
					break;
				case "corporate":
					helper = new CorporateHelper((Corporate) employee);
					_controller = new CorporateController((CorporateHelper) helper);
	                promptUser(in);
					break;
				default:
					System.out.println();
					System.out.println("Invalid username or password");
					System.out.println();
					break;
			}

			System.out.println("Enter 'exit' to close application, anything else to continue: ");
			if(in.nextLine().equals("exit")) exit = true;
		}

		System.out.println("\nClosing application...\n");
		in.close();
	}

	public void promptUser(Scanner reader){
		boolean isSignedIn = true;
		
		System.out.println();
		System.out.println("Welcome! Enter 'exit' to sign out.");
		System.out.println();

		while(isSignedIn){
			System.out.println("Enter a command: ");
			String cmd = reader.nextLine();
			List<String> result = Arrays.asList(cmd.split(";"));
			isSignedIn = !result.get(0).equals("exit");
			if (isSignedIn){
                try{
                    _controller.execute(result);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
			else
			{
				System.out.println("\nSigning out...\n");
			}
		}
	}
	
	private Employee validateUser(String username, String password)
	{
		Employee employee = service.cashier().findByUsername(username);
		employee = employee == null ? service.manager().findByUsername(username) : employee;
		employee = employee == null ? service.corporate().findByUsername(username) : employee;
		
		return (employee != null && employee.getPassword().equals(password)) ? employee : null;
	}
}
