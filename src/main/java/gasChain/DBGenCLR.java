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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gasChain.entity.Employee;
import gasChain.entity.GasStation;
import gasChain.entity.Sale;
import gasChain.service.EmployeeService;
import gasChain.service.GasStationService;
import gasChain.service.SaleService;

@Component
public class DBGenCLR implements CommandLineRunner {
	
//	@Autowired
//	EmployeeService employeeService;
	@Autowired
	GasStationService gasStationService;
//	@Autowired
//	SaleService saleService;
	
	@Override
	public void run(String... args) throws Exception 
	{
		Path curRelPath = Paths.get("");
		String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\dbGeneratorData";
		generateGasStations(corePath);
	}
	
	private void generateGasStations(String corePath) throws FileNotFoundException
	{
		Random rng = new Random();
		int maxSales = 500;
		int minEmployees = 3; int maxEmployees = 8;
		
		ArrayList<GasStation> gasStations = getGasStations(new File(corePath + "\\locationNames"));
		ArrayList<Sale> saleTemplates = getSaleTemplates(new File(corePath + "\\itemTypes"));
		ArrayList<String> firstNames = getNames(new File(corePath + "\\employeeNamesFirst"));
		ArrayList<String> lastNames = getNames(new File(corePath + "\\employeeNamesLast"));
		
		for(GasStation gasStation : gasStations)
		{
			int numSales = rng.nextInt() % maxSales;
			
			// Generate sales
			while(numSales > 0) 
			{ 
				gasStation.addSale( 
						new Sale(
						saleTemplates.get(rng.nextInt(saleTemplates.size())),
						gasStation));
				numSales--;
			}
			
			int numEmployees = rng.nextInt() % (maxEmployees - minEmployees) + minEmployees;

			// Generate employees
			while(numEmployees > 0)
			{
				String name = firstNames.get(rng.nextInt(firstNames.size()))
								+ " " + lastNames.get(rng.nextInt(lastNames.size()));
				gasStation.addEmployee(new Employee(name, 10.0f, 30));
				numEmployees--;
			}

			gasStationService.add(gasStation);
		}
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
		int year = 2019 - (rng.nextInt() % yearRange);
		int month = rng.nextInt() % 12;
		int day = rng.nextInt() % 365;
		
		return new Date(year, month, day);
	}
}
