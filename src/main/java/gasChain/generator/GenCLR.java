package gasChain.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import gasChain.GasStationChainApplication;
import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Manager;
import gasChain.entity.Receipt;
import gasChain.entity.Sale;
import gasChain.service.CorporateService;
import gasChain.service.EmployeeService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import gasChain.service.SaleService;
import gasChain.service.ServiceMaster;

@Component
@Order(1)
public class GenCLR implements CommandLineRunner {

	@Autowired
	private ServiceMaster service;

    private static Logger LOG = LoggerFactory
    	      .getLogger(GasStationChainApplication.class);

    private static GenDataRepository repo;

    // If the database is empty, generates gas stations and their sales, cashiers, and manager
	@Override
	public void run(String... args) throws Exception
	{
		if(service.gasStation().isEmpty()) { run2(); }
	}

	private void run2() throws Exception
	{
		long startTime = System.currentTimeMillis();
        LOG.info("Starting database generation...");
        
        // Get file-read data
		Path curRelPath = Paths.get("");
		String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\dbGeneratorData";
		repo = new GenDataRepository(corePath);

		//Generate items
		List<Item> items = repo.items();
		for(Item item : items) { service.item().save(item); }
		
		// Generate corporate employees
		int numCorporate = 30;
		generateCorporates(numCorporate, repo.firstNames(), repo.lastNames());
		
		//Generate gas stations and their employees, sales, and inventory
		int maxSales = 500;
		int minEmployees = 3; int maxEmployees = 8;
		generateGasStations(maxSales, minEmployees, maxEmployees);
		
        long endTime = System.currentTimeMillis();
        LOG.info("...finished database generation in " + ((endTime - startTime) / 1000) + " seconds");
	}

	// Pulls data for locations, items, and names from txt files for generation, generates database
	private void generateGasStations(int maxSales, int minEmployees, int maxEmployees)
	{
		ArrayList<GasStation> gasStations = repo.produceGasStations();
		for(GasStation gasStation : gasStations)
		{
			service.gasStation().save(gasStation);
			
			generateSales(maxSales, gasStation, repo.items());
			generateCashiers(minEmployees, maxEmployees, gasStation, repo.firstNames(), repo.lastNames());
			service.gasStation().save(gasStation);

			generateManager(gasStation);

			service.gasStation().save(gasStation);
		}
	}

	private void generateSales(int maxSales, GasStation gasStation, ArrayList<Item> items)
	{
		int numSales = GenUtil.rng.nextInt(maxSales);
		while(numSales > 0)
		{
			Receipt receipt = new Receipt();
			Item item = service.item().findAll().get(GenUtil.rng.nextInt(items.size()));
			for(int numReceipt = GenUtil.rng.nextInt(8); numReceipt > 0; numReceipt--)
			{
				Sale sale = new Sale(item, gasStation, receipt, 
									item.getSuggestRetailPrice(), GenUtil.genDate());
				gasStation.addSale(sale);
				receipt.addSale(sale);
				numSales--;
			}
			service.receipt().save(receipt);
		}
	}

	private void generateCashiers(int minEmployees, int maxEmployees, GasStation gasStation,
									ArrayList<String> firstNames, ArrayList<String> lastNames)
	{
		int numEmployees = GenUtil.rng.nextInt(maxEmployees - minEmployees) + minEmployees;
		float minWage = 8.5f; float maxWage = 15.0f;
		int minHours = 20; int maxHours = 50;

		while(numEmployees > 0)
		{
			float wage = GenUtil.rng.nextFloat() * (maxWage - minWage) + minWage;
			wage = ((float) (new Float(wage * 100.0f)).intValue()) / 100.0f;
			int hours = GenUtil.rng.nextInt(maxHours - minHours) + minHours;
			String name = GenUtil.genRandomName(firstNames, lastNames);
			Cashier cashier = new Cashier(name, wage, hours, gasStation);
			gasStation.addCashier(cashier);
			service.cashier().save(cashier);
			numEmployees--;
		}
	}

	private void generateManager(GasStation gasStation)
	{
		Manager manager = new Manager( gasStation.getLocation() + "_Manager", "password");
		manager.setStore(gasStation);
		gasStation.setManager(manager);
		service.manager().save(manager);
	}

	private void generateCorporates(int num, ArrayList<String> firstNames, ArrayList<String> lastNames)
	{
		for(int i = 0; i < num; i++)
		{
			String username = GenUtil.genRandomName(firstNames, lastNames).replace(' ', '_')
					+ (new Integer(GenUtil.rng.nextInt(100))).toString();

			service.corporate().save(new Corporate(username, "password"));
		}
	}

}