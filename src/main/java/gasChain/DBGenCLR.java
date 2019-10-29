package gasChain;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.entity.Sale;
import gasChain.service.CorporateService;
import gasChain.service.EmployeeService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import gasChain.service.SaleService;

@Component
@Order(1)
public class DBGenCLR implements CommandLineRunner {
	
	@Autowired
	GasStationService gasStationService;
	@Autowired
	ManagerService managerService;
	@Autowired
	CorporateService corporateService;
	
    private static Logger LOG = LoggerFactory
    	      .getLogger(GasStationChainApplication.class);
    
    private static Random rng;
	
	@Override
	public void run(String... args) throws Exception 
	{
		if(gasStationService.isEmpty()) { run2(); }
	}
	
	private void run2() throws Exception
	{
		rng = new Random();
		long startTime = System.currentTimeMillis();
        LOG.info("Starting database generation...");
		Path curRelPath = Paths.get("");
		String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\dbGeneratorData";
		generateGasStations(corePath);
        long endTime = System.currentTimeMillis();
        LOG.info("...finished database generation in " + ((endTime - startTime) / 1000) + " seconds");

	}
	
	private void generateGasStations(String corePath) throws FileNotFoundException
	{
		// TODO - flesh out?
		corporateService.add(new Corporate("Test Corporate", "password"));
		
		int maxSales = 500;
		int minEmployees = 3; int maxEmployees = 8;
		
		ArrayList<GasStation> gasStations = getGasStations(new File(corePath + "\\locationNames"));
		ArrayList<Sale> saleTemplates = getSaleTemplates(new File(corePath + "\\itemTypes"));
		ArrayList<String> firstNames = getNames(new File(corePath + "\\employeeNamesFirst"));
		ArrayList<String> lastNames = getNames(new File(corePath + "\\employeeNamesLast"));
		
		for(GasStation gasStation : gasStations)
		{
			generateSales(maxSales, gasStation, saleTemplates);
			generateCashiers(minEmployees, maxEmployees, gasStation, firstNames, lastNames);
			gasStationService.add(gasStation);
			
			generateManager(gasStation);

			gasStationService.update(gasStation);
		}
	}

	private void generateSales(int maxSales, GasStation gasStation, ArrayList<Sale> saleTemplates)
	{
		for(int numSales = rng.nextInt(maxSales); numSales > 0; numSales--) 
		{ 
			gasStation.addSale(
					new Sale(
					saleTemplates.get(rng.nextInt(saleTemplates.size())),
					genDate(),
					gasStation));
		}
	}
	
	private void generateCashiers(int minEmployees, int maxEmployees, GasStation gasStation,
									ArrayList<String> firstNames, ArrayList<String> lastNames)
	{
		int numEmployees = rng.nextInt(maxEmployees - minEmployees) + minEmployees;
		float minWage = 8.5f; float maxWage = 15.0f;
		int minHours = 20; int maxHours = 50;
		
		while(numEmployees > 0)
		{
			float wage = rng.nextFloat() * (maxWage - minWage) + minWage;
			wage = ((float) (new Float(wage * 100.0f)).intValue()) / 100.0f;
			int hours = rng.nextInt(maxHours - minHours) + minHours;
			String name = firstNames.get(rng.nextInt(firstNames.size()))
							+ " " + lastNames.get(rng.nextInt(lastNames.size()));
			gasStation.addCashier(new Cashier(name, wage, hours, gasStation));
			numEmployees--;
		}
	}
	
	private void generateManager(GasStation gasStation)
	{
		Manager manager = new Manager( gasStation.getLocation() + " Manager", "password");
		manager.setStore(gasStation);
		gasStation.setManager(manager);
		managerService.add(manager);
	}
	
	private ArrayList<GasStation> getGasStations(File file) throws FileNotFoundException
	{
		ArrayList<GasStation> locations = new ArrayList<GasStation>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt(); scan.nextLine();
		
		while(scan.hasNext())
		{
			String[] next = scan.nextLine().split(";");
			switch(version)
			{
			case 0:
				locations.add(new GasStation(next[0]));
				break;
			case 1:
				locations.add(new GasStation(next[0], next[1], next[2]));
				break;
			default:
				locations.add(new GasStation("Error"));
				break;
			}
		}
		
		scan.close();
		return locations;
	}

	private ArrayList<Sale> getSaleTemplates(File file) throws FileNotFoundException
	{
		ArrayList<Sale> templates = new ArrayList<Sale>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt(); scan.nextLine();
		
		while(scan.hasNext())
		{
			String[] next = scan.nextLine().split(";");
			switch(version)
			{
			case 0:
				templates.add(new Sale(next[0], Float.parseFloat(next[1]), genDate()));
				break;
			default:
				templates.add(new Sale("Error", -1.00f, genDate()));
				break;
			}
		}
		
		scan.close();
		return templates;
	}
	
	private ArrayList<String> getNames(File file) throws FileNotFoundException
	{
		ArrayList<String> names = new ArrayList<String>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt(); scan.nextLine();

		while(scan.hasNext())
		{
			names.add(scan.nextLine());
		}
		
		scan.close();
		return names;
	}
	
	private Date genDate()
	{
		int yearRange = 5;
		Random rng = new Random();
		String year = Integer.toString(2019 - rng.nextInt(yearRange));
		String month = Integer.toString((rng.nextInt(12) + 1));
		String day = Integer.toString((rng.nextInt(28) + 1));
		
		String sdf = year + "-" + month + "-" + day;
		
		return Date.valueOf(sdf);
	}
}
