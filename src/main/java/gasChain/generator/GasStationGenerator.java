package gasChain.generator;

import gasChain.entity.*;
import gasChain.util.ServiceMaster;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GasStationGenerator {
    private int counter = 0;
    private ServiceMaster service;
    private GenDataRepository repo;

    public GasStationGenerator(ServiceMaster service, GenDataRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    public void execute() {
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
            generateExpenses(gasStation);
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

            int paymentType = GenUtil.rng.nextInt(3) + 1;
            receipt.setPayment(Receipt.Payment.values()[paymentType]);

            service.receipt().save(receipt);
        }
    }
    
    private void generateExpenses(GasStation gasStation)
    {
    	// Note - costs are in cents
    	int minCostUtility = 10000; int maxCostUtility = 60000; 
    	int rangeUtility = maxCostUtility - minCostUtility;
    	int minCostInsurance = 400000; int maxCostInsurance = 1000000;
    	int rangeInsurance = maxCostInsurance - minCostInsurance;
    	
    	int electric = GenUtil.rng.nextInt(rangeUtility) + minCostUtility;
        int water = GenUtil.rng.nextInt(rangeUtility) + minCostUtility;
        int sewage = GenUtil.rng.nextInt(rangeUtility) + minCostUtility;
        int garbage = GenUtil.rng.nextInt(rangeUtility) + minCostUtility;
        int insurance = GenUtil.rng.nextInt(rangeInsurance) + minCostInsurance;
        
        gasStation.setExpenses(new Expenses(electric, water, sewage, garbage, insurance));
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

            if (!service.cashier().existsUser(cashier.getUsername())) {
            	generateCashierWorkPeriods(cashier);
                gasStation.addCashier(cashier);
                service.cashier().save(cashier);
                numEmployees--;
                generateRewardMembershipAccounts(cashier, firstNames, lastNames);
            }
        }
    }

    private void generateRewardMembershipAccounts(Cashier cashier, ArrayList<String> firstNames, ArrayList<String> lastNames) {
        int numAccounts = GenUtil.rng.nextInt(100) + 2;
        List<Receipt> receipts = service.receipt().findAllByRewardMembershipAccountIsNull();
        System.out.println("Percent complete (Very rough): " + (counter++ / 450.00) + "%");
        for (int i = 0; i < numAccounts; i++) {
            String email = GenUtil.genRandomEmail();
            String name = GenUtil.genRandomName(firstNames, lastNames);
            String phoneNumber = GenUtil.genRandonPhoneNumber();

            RewardMembershipAccount rewardMembershipAccount = new RewardMembershipAccount(email, name, phoneNumber, cashier);
            rewardMembershipAccount.setCreatedOn(GenUtil.genDate());
            service.rewardMembershipAccount().save(rewardMembershipAccount);

            int numReceipt = receipts.size() / (numAccounts - i) > 0 ? GenUtil.rng.nextInt(receipts.size() / (numAccounts - i)) : 0;
            for (int j = 0; j < numReceipt; j++) {
                int index = GenUtil.rng.nextInt(receipts.size() - 1);
                Receipt r = receipts.get(index);
                r.setRewardMembershipAccount(rewardMembershipAccount);
                service.receipt().save(r);
                receipts.remove(index);
            }
        }
    }
    private void generateCashierWorkPeriods(Cashier cashier)
    {
    	int numWorkPeriods = 50;
    	int maxHours = 8; int maxStartHour = 24 - maxHours;
    	
    	while(numWorkPeriods > 0 )
    	{
    		int startHour = GenUtil.rng.nextInt(maxStartHour);
    		int endHour = startHour + GenUtil.rng.nextInt(maxHours);
    		Date date = GenUtil.genDate();
    		cashier.addWorkPeriod(new WorkPeriod(cashier, startHour, endHour, 
    											cashier.getWagesHourly(), date));
    		numWorkPeriods--;
    	}
    }

    private void generateManager(GasStation gasStation) {
        Manager manager = new Manager(gasStation.getLocation() + "_Manager", "password");
        manager.setStore(gasStation);
        gasStation.setManager(manager);
        service.manager().save(manager);
    }

    private void generateGasStationInventory(GasStation gasStation) {
        ArrayList<GasStationInventory> inventory = getGasStationInventory();
        for (GasStationInventory item : inventory) {
            item.setGasStation(gasStation);
            service.gasStationInventory().save(item);
            gasStation.addInventory(item);
        }
    }

    private ArrayList<GasStationInventory> getGasStationInventory() {
        ArrayList<GasStationInventory> inventory = new ArrayList<GasStationInventory>();
        ArrayList<Item> items = (ArrayList<Item>) service.item().findAll();

        for (int i = GenUtil.rng.nextInt(2); i < items.size(); i += GenUtil.rng.nextInt(2)) {
            Item item = items.get(i);
            inventory.add(new GasStationInventory(item, item.getSuggestRetailPrice(), GenUtil.rng.nextInt(50)));
        }

        return inventory;
    }
}
