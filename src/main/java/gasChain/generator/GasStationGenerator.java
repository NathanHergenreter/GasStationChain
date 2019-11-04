package gasChain.generator;

import java.util.ArrayList;


import gasChain.entity.CashPayment;
import gasChain.entity.Cashier;
import gasChain.entity.CreditCardAccount;
import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;
import gasChain.entity.Manager;
import gasChain.entity.Payment;
import gasChain.entity.Receipt;
import gasChain.entity.Sale;
import gasChain.entity.WarehouseInventory;
import gasChain.service.ServiceMaster;

public class GasStationGenerator {

	private ServiceMaster service;
	private GenDataRepository repo;
	
	public GasStationGenerator(ServiceMaster service, GenDataRepository repo) 
	{ 
		this.service = service; this.repo = repo; 
	}
	
	public void execute()
	{
		// Values used for determining num's created
		int maxSales = 500;
		int minEmployees = 3;
		int maxEmployees = 8;
		
		generateGasStations(maxSales, minEmployees, maxEmployees);
	}
	
	// Pulls data for locations, items, and names from txt files for generation,
	// generates database
	private void generateGasStations(int maxSales, int minEmployees, int maxEmployees) {
		ArrayList<GasStation> gasStations = repo.produceGasStations();
		
		for (GasStation gasStation : gasStations) {
			service.gasStation().save(gasStation);

			generateSales(maxSales, gasStation, repo.items());
			generateCashiers(minEmployees, maxEmployees, gasStation, repo.firstNames(), repo.lastNames());
			service.gasStation().save(gasStation);

			generateManager(gasStation);

			service.gasStation().save(gasStation);
			
			generateGasStationInventory(gasStation);
		}
	}

	private void generateSales(int maxSales, GasStation gasStation, ArrayList<Item> items) {
		int numSales = GenUtil.rng.nextInt(maxSales);
		while (numSales > 0) {
			Receipt receipt = new Receipt();
			Item item = service.item().findAll().get(GenUtil.rng.nextInt(items.size()));
			for (int numReceipt = GenUtil.rng.nextInt(8); numReceipt > 0; numReceipt--) {
				Sale sale = new Sale(item, gasStation, receipt, item.getSuggestRetailPrice(), GenUtil.genDate());
				gasStation.addSale(sale);
				receipt.addSale(sale);
				numSales--;
			}
			Payment p;
			try {
				p = new CreditCardAccount("2238467265875675");
				service.creditCardAccount().save((CreditCardAccount) p);
			} catch (Exception e) {
				p = new CashPayment();
				service.cashPayment().save((CashPayment) p);
			}
			receipt.setPayment(p);

			service.receipt().save(receipt);


//			List<Sale> sales = receipt.getSales();
//			for(Sale s: sales){
//				service.sale().save(s);
//			}

		}
	}

	private void generateCashiers(int minEmployees, int maxEmployees, GasStation gasStation,
			ArrayList<String> firstNames, ArrayList<String> lastNames) {
		int numEmployees = GenUtil.rng.nextInt(maxEmployees - minEmployees) + minEmployees;
		float minWage = 8.5f;
		float maxWage = 15.0f;
		int minHours = 20;
		int maxHours = 50;

		while (numEmployees > 0) {
			float wage = GenUtil.rng.nextFloat() * (maxWage - minWage) + minWage;
			wage = ((float) (new Float(wage * 100.0f)).intValue()) / 100.0f;
			int hours = GenUtil.rng.nextInt(maxHours - minHours) + minHours;
			String name = GenUtil.genRandomName(firstNames, lastNames);
			Cashier cashier = new Cashier(name, (int) (wage * 100), hours, gasStation);
			gasStation.addCashier(cashier);
			service.cashier().save(cashier);
			numEmployees--;
		}
	}

	private void generateManager(GasStation gasStation) {
		Manager manager = new Manager(gasStation.getLocation() + "_Manager", "password");
		manager.setStore(gasStation);
		gasStation.setManager(manager);
		service.manager().save(manager);
	}
	
	private void generateGasStationInventory(GasStation gasStation)
	{
		ArrayList<GasStationInventory> inventory = getGasStationInventory();
		for(GasStationInventory item : inventory)
		{
			item.setGasStation(gasStation);
			service.gasStationInventory().save(item);
			gasStation.addInventory(item);
		}
			
	}
	
	private ArrayList<GasStationInventory> getGasStationInventory()
	{
		ArrayList<GasStationInventory> inventory = new ArrayList<GasStationInventory>();
		ArrayList<Item> items = (ArrayList<Item>) service.item().findAll();
		
		for(int i = GenUtil.rng.nextInt(2); i < items.size(); i += GenUtil.rng.nextInt(2) )
		{
			Item item = items.get(i);
			inventory.add(new GasStationInventory(item, item.getSuggestRetailPrice(), GenUtil.rng.nextInt(50)));
		}
		
		return inventory;
	}
}
