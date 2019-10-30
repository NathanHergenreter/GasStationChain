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
import gasChain.entity.GasStation;
import gasChain.service.GasStationService;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {
	@Override
	public void run(String... args) throws Exception {
		System.out.println("Enter username: ");
		//TODO: validate user here...
		//if user instanceOf cashier, promptCashier()
		//elif user instanceOf cashier, promptStoreManager()
		//elif user instanceOf cashier, promptCorporateEmployee()
		//else prompt "invalid username"...
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
