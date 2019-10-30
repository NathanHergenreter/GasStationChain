package gasChain.application;

import gasChain.userControllers.CashierController;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gasChain.coreInterfaces.userControllers.IUserController;
import gasChain.entity.Employee;
import gasChain.entity.GasStation;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {
	
	@Autowired
	CashierService cashierService;
	@Autowired
	ManagerService managerService;
	@Autowired
	CorporateService corporateService;
	
	static Scanner in;
	
	@Override
	public void run(String... args) throws Exception {
		in = new Scanner(System.in);
		
		System.out.println("Enter username: ");
		String username = in.next();
		
		Employee employee = cashierService.findByUsername(username);
		employee = employee == null ? managerService.findByUsername(username) : employee;
		employee = employee == null ? corporateService.findByUsername(username) : employee;
		
		switch(employee.getAuth())
		{
			case "cashier":
				promptCashier();
				break;
			case "manager":
				promptStoreManager();
				break;
			case "corporate":
				promptCorporateEmployee();
				break;
			default:
				System.out.println("Invalid username - User does not exist");
				break;
		}
		
		in.close();
	}

	IUserController _controller;

	public void promptCashier(){
		boolean isSignedIn = true;
		_controller = new CashierController();
		while(!isSignedIn){
			//prompt for respective cashier commands
			//List<String> result = Arrays.asList(Scanner.scan().split("-"));
			//_controller.execute(result);
			// ^^^this method should switch on first e in list to find the resp. cmd
			// and pass in the rest of the params to that method from the given list

		}
	}

	public void promptStoreManager(){
		boolean isSignedIn = true;
		while(!isSignedIn){
			//prompt for respective cashier commands
		}
	}

	public void promptCorporateEmployee(){
		boolean isSignedIn = true;
		while(!isSignedIn){
			//prompt for respective cashier commands
		}
	}
}
