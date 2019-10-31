package gasChain.application;

import gasChain.coreInterfaces.managers.ICashierHelper;
import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.coreInterfaces.managers.IUserHelper;
import gasChain.entity.Cashier;
import gasChain.entity.Manager;
import gasChain.managers.CashierHelper;
import gasChain.managers.ManagerHelper;
import gasChain.userControllers.CashierController;
import java.util.Scanner;

import gasChain.userControllers.ManagerController;
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
		IUserHelper helper;

		switch(employee.getAuth())
		{
			case "cashier":
				helper = new CashierHelper((Cashier)employee);
                _controller = new CashierController((CashierHelper)helper);
                promptUser(in);
				break;
			case "manager":
			    helper = new ManagerHelper((Manager) employee);
			    _controller = new ManagerController((ManagerHelper)helper);
                promptUser(in);
				break;
			case "corporate":
                promptUser(in);
				break;
			default:
				System.out.println("Invalid username - User does not exist");
				break;
		}
		
		in.close();
	}

	IUserController _controller;

	public void promptUser(Scanner reader){
		boolean isSignedIn = true;

		while(isSignedIn){
			List<String> result = Arrays.asList(reader.nextLine().split("-"));
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
}
