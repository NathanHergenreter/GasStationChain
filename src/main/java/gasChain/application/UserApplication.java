package gasChain.application;

import gasChain.coreInterfaces.managers.IUserHelper;
import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.Employee;
import gasChain.entity.Manager;
import gasChain.managers.CashierHelper;
import gasChain.managers.CorporateHelper;
import gasChain.managers.ManagerHelper;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.ManagerService;
import gasChain.service.ServiceMaster;
import gasChain.userControllers.CashierController;
import gasChain.userControllers.CorporateController;
import gasChain.userControllers.ManagerController;
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
		boolean hasValidated = false;
		
		while(!hasValidated)
		{
			System.out.println("Enter username: ");
			String username = in.next();
			
			System.out.println("Enter password: ");
			String password = in.next();
			
			IUserHelper helper;
	
			Employee employee = validateUser(username, password);
			String auth = employee != null ? employee.getAuth() : "ERROR";
			hasValidated = true;
			
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
					hasValidated = false;
					System.out.println("Invalid username or password");
					break;
			}
		}

		in.close();
	}

	public void promptUser(Scanner reader){
		boolean isSignedIn = true;

		while(isSignedIn){
			System.out.println("Enter a command: ");
			String cmd = reader.next();
			List<String> result = Arrays.asList(cmd.split("-"));
			isSignedIn = !result.get(0).equals("exit");
			if (isSignedIn){
                try{
                    _controller.execute(result);
                }catch (Exception e){
                    System.out.println(e);
                }
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
