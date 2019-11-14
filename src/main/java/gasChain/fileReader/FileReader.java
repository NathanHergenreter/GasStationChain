package gasChain.fileReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.Expenses;
import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;
import gasChain.entity.Manager;
import gasChain.entity.Warehouse;
import gasChain.entity.WarehouseInventory;
import gasChain.entity.WorkPeriod;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.ExpensesService;
import gasChain.service.GasStationInventoryService;
import gasChain.service.GasStationService;
import gasChain.service.ItemService;
import gasChain.service.ManagerService;
import gasChain.service.WarehouseInventoryService;
import gasChain.service.WarehouseService;
import gasChain.service.WorkPeriodService;
import gasChain.util.ServiceAutoWire;

public class FileReader {

    private static CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);
    private static CorporateService corporateService = ServiceAutoWire.getBean(CorporateService.class);
    private static ExpensesService expensesService = ServiceAutoWire.getBean(ExpensesService.class);
    private static GasStationService gasStationService = ServiceAutoWire.getBean(GasStationService.class);
    private static GasStationInventoryService gasStationInventoryService = ServiceAutoWire.getBean(GasStationInventoryService.class);
    private static ItemService itemService = ServiceAutoWire.getBean(ItemService.class);
    private static ManagerService managerService = ServiceAutoWire.getBean(ManagerService.class);
    private static WarehouseService warehouseService = ServiceAutoWire.getBean(WarehouseService.class);
    private static WarehouseInventoryService warehouseInventoryService = ServiceAutoWire.getBean(WarehouseInventoryService.class);
    private static WorkPeriodService workPeriodService = ServiceAutoWire.getBean(WorkPeriodService.class);
    
    public static void readFile(String filename) throws Exception
    {
        Path curRelPath = Paths.get("");
        String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\tables\\";
        Scanner in = new Scanner(new File(corePath + filename));
        
        while(in.hasNextLine())
        {
        	String tableType = in.nextLine();
        	
        	switch(tableType)
        	{
        		case "CashierTable":
        			readCashierTable(in);
        			break;

        		case "CorporateTable":
        			readCorporateTable(in);
        			break;

        		case "ExpensesTable":
        			readExpensesTable(in);
        			break;

        		case "GasStationTable":
        			readGasStationTable(in);
        			break;

        		case "GasStationInventoryTable":
        			readGasStationInventoryTable(in);
        			break;

        		case "ItemTable":
        			readItemTable(in);
        			break;

        		case "ManagerTable":
        			readManagerTable(in);
        			break;

        		case "WarehouseTable":
        			readWarehouseTable(in);
        			break;

        		case "WarehouseInventoryTable":
        			readWarehouseInventoryTable(in);
        			break;

        		case "WorkPeriodTable":
        			readWorkPeriodTable(in);
        			break;
        			
    			default:
    				break;
        	}
        }
    }
    
    private static void readCashierTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 6)
                throw new Exception("Invalid cashier table");
            
            String username = row.get(0);
            String password = row.get(1);
            String name = row.get(2);
            int wagesHourly = new Integer(row.get(3));
            int hoursWeekly = new Integer(row.get(4));
            String location = row.get(5);
            GasStation workplace = gasStationService.findByLocation(location);
            
            if(workplace == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");
            
            Cashier cashier = new Cashier(username, password, name, 
        								wagesHourly, hoursWeekly, workplace);
            
            if(!cashierService.existsUser(cashier.getUsername()))
            {
            	cashierService.save(cashier);
            }
    	}
    }
    
    private static void readManagerTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 3)
                throw new Exception("Invalid manager table");
            
            String username = row.get(0);
            String password = row.get(1);
            String location = row.get(2);
            GasStation workplace = gasStationService.findByLocation(location);

            if(workplace == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");
            
            Manager manager = new Manager(username, password);
            
            if(!managerService.existsUser(manager.getUsername()))
            {
                manager.setStore(workplace);
                workplace.setManager(manager);
	        	managerService.save(manager);
	        	gasStationService.save(workplace);
            }
    	}
    }

    private static void readCorporateTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 2)
                throw new Exception("Invalid corporate table");
            
            String username = row.get(0);
            String password = row.get(1);
            
            Corporate corporate = new Corporate(username, password);
            
            if(!corporateService.existsUser(corporate.getUsername()))
            {
            	corporateService.save(corporate);
            }
    	}
    }

    private static void readWorkPeriodTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 5)
                throw new Exception("Invalid work period table");
            
            String username = row.get(0);
            int startHour = new Integer(row.get(1));
            int endHour = new Integer(row.get(2));
            int wages = new Integer(row.get(3));
            Date date = Date.valueOf(row.get(4));
            Cashier cashier = (Cashier) cashierService.findByUsername(username);
            
            if(cashier == null)
                throw new Exception("User of username '" + username + "' does not exist");
            
            WorkPeriod workPeriod = new WorkPeriod(cashier, startHour, endHour, wages, date);
            
        	workPeriodService.save(workPeriod);
        	cashierService.save(cashier);
    	}
    }

    private static void readGasStationTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 3)
                throw new Exception("Invalid gas station table");
            
            String location = row.get(0);
            String state = row.get(1);
            String region = row.get(2);
            
            GasStation gasStation = new GasStation(location, state, region);
            
            if(!gasStationService.existsLocation(location))
            {
            	gasStationService.save(gasStation);
            }
    	}
    }

    private static void readWarehouseTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 3)
                throw new Exception("Invalid warehouse table");
            
            String location = row.get(0);
            String state = row.get(1);
            String region = row.get(2);
            
            Warehouse warehouse = new Warehouse(location, state, region);
            
            if(!warehouseService.existsLocation(location))
            {
            	warehouseService.save(warehouse);
            }
    	}
    }

    private static void readItemTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 2)
                throw new Exception("Invalid item table");
            
            String name = row.get(0);
            int price = new Integer(row.get(1));
            
            Item item = new Item(name, price);
            
            if(!itemService.existsItem(name))
            {
            	itemService.save(item);
            }
    	}
    }
    
    private static void readGasStationInventoryTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(!(row.size() == 4 || row.size() == 5))
                throw new Exception("Invalid gas station inventory table");
            
            String location = row.get(0);
            String itemType = row.get(1);
            int price = new Integer(row.get(2));
            int quantity = new Integer(row.get(3));
            int maxQuantity = row.size() == 5 ? new Integer(row.get(4)) : quantity;
            GasStation gasStation = gasStationService.findByLocation(location);
            
            if(gasStation == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");

            Item item = itemService.findByName(itemType);
            
            if(item == null)
                throw new Exception("Item of type '" + itemType + "' does not exist. Please enter item into system first");
            
            GasStationInventory inventory = new GasStationInventory(item, price, quantity, maxQuantity);
            
            if(!gasStation.hasInventory(item))
            {
            	inventory.setGasStation(gasStation);
            	gasStationInventoryService.save(inventory);
            }
    	}
    }
    
    private static void readWarehouseInventoryTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 4)
                throw new Exception("Invalid warehouse inventory table");
            
            String location = row.get(0);
            String itemType = row.get(1);
            int price = new Integer(row.get(2));
            int quantity = new Integer(row.get(3));
            Warehouse warehouse = warehouseService.findByLocation(location);
            
            if(warehouse == null)
                throw new Exception("Warehouse at location '" + location + "' does not exist");

            Item item = itemService.findByName(itemType);
            
            if(item == null)
                throw new Exception("Item of type '" + itemType + "' does not exist. Please enter item into system first");
            
            WarehouseInventory inventory = new WarehouseInventory(item, price, quantity);
            
            if(warehouse.findWarehouseInventory(item) == null)
            {
            	inventory.setWarehouse(warehouse);
            	warehouseInventoryService.save(inventory);
            }
    	}
    }

    private static void readExpensesTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 6)
                throw new Exception("Invalid expenses table");
            
            String location = row.get(0);
            int electric = new Integer(row.get(1));
            int water = new Integer(row.get(2));
            int sewage = new Integer(row.get(3));
            int garbage = new Integer(row.get(4));
            int insurance = new Integer(row.get(5));
            GasStation gasStation = gasStationService.findByLocation(location);

            if(gasStation == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");
            
            Expenses expenses = new Expenses(electric, water, sewage, garbage, insurance);
            
            if(gasStation.getExpenses() == null) { gasStation.setExpenses(expenses); }
            else { gasStation.updateExpenses(expenses); }
            
            gasStationService.save(gasStation);
    	}
    }
    
}
