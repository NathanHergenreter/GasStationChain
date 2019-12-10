package gasChain.application.manager;

import gasChain.annotation.CorporateUser;
import gasChain.annotation.MethodHelp;
import gasChain.entity.*;
import gasChain.fileReader.FileReader;
import gasChain.service.*;
import gasChain.util.ServiceAutoWire;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CorporateHelper {

    private static GasStationService gasStationService = ServiceAutoWire.getBean(GasStationService.class);
    private static CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);
    private static CorporateService corporateService = ServiceAutoWire.getBean(CorporateService.class);
    private static ManagerService managerService = ServiceAutoWire.getBean(ManagerService.class);
    private static WarehouseInventoryService warehouseInventoryService = ServiceAutoWire.getBean(WarehouseInventoryService.class);
    private static WarehouseService warehouseService = ServiceAutoWire.getBean(WarehouseService.class);
    private static ItemService itemService = ServiceAutoWire.getBean(ItemService.class);
    private static SaleService saleService = ServiceAutoWire.getBean(SaleService.class);
    private static WorkPeriodService workPeriodService = ServiceAutoWire.getBean(WorkPeriodService.class);

    @CorporateUser(command = "AddManager", parameterEquation = "p == 3")
    public static void addManager(List<String> args, Corporate corporate) throws Exception {
        String username = args.get(0);
        String password = args.get(1);
        String location = args.get(2);
        GasStation store = gasStationService.findByLocation(location);

        if (cashierService.existsUser(username)
                || managerService.existsUser(username)
                || corporateService.existsUser(username)
        ) {
            throw new Exception("Username '" + username + "' taken.");
        }

        if (store == null) {
            throw new Exception("Location '" + location + "' does not exist.");
        }

        Manager manager = new Manager(username, password);
        manager.setStore(store);
        managerService.save(manager);
        store.setManager(manager);
        gasStationService.save(store);
//        managerService.save(manager);
    }

    @CorporateUser(command = "RemoveManager", parameterEquation = "p == 1")
    public static void removeManager(List<String> args, Corporate corporate) throws Exception {

        String username = args.get(0);

        if (!managerService.existsUser(username)) {
            throw new Exception("Manager of username '" + username + "' does not exist.");
        }

        Manager manager = (Manager) managerService.findByUsername(username);
        GasStation store = manager.getStore();
        if (store != null) {
            store.setManager(null);
        }
        manager.setStore(null);

        if (store != null) {
            gasStationService.save(store);
        }
        managerService.delete(manager);
    }

    @CorporateUser(command = "AddGasStation", parameterEquation = "p == 3")
    public static void addGasStation(List<String> args, Corporate corporate) throws Exception {

        String location = args.get(0);
        String state = args.get(1);
        String region = args.get(2);

        if (gasStationService.existsLocation(location)) {
            throw new Exception("Gas station at location '" + location + "' already exists.");
        }
        gasStationService.save(new GasStation(location, state, region));
    }

    @CorporateUser(command = "RemoveGasStation", parameterEquation = "p == 1")
    public static void removeGasStation(List<String> args, Corporate corporate) throws Exception {

        String location = args.get(0);

        if (!gasStationService.existsLocation(location)) {

            throw new Exception("Gas station at location '" + location + "' does not exist.");
        }

        GasStation store = gasStationService.findByLocation(location);
        Manager manager = store.getManager();

        if (manager != null) {

            manager.setStore(null);
        }
        System.out.println("RemoveGasStation5");
        store.setManager(null);

        if (manager != null) {

            managerService.save(manager);
        }

        gasStationService.delete(store);
    }

    @CorporateUser(command = "AddWareHouse", parameterEquation = "p == 3")
    public static void addWarehouse(List<String> args, Corporate corporate) throws Exception {

        String location = args.get(0);
        String state = args.get(1);
        String region = args.get(2);

        if (warehouseService.existsLocation(location)) {
            throw new Exception("Warehouse at location '" + location + "' already exists.");
        }

        warehouseService.save(new Warehouse(location, state, region));
    }

    @CorporateUser(command = "RemoveWarehouse", parameterEquation = "p == 1")
    public static void removeWarehouse(List<String> args, Corporate corporate) throws Exception {
        String location = args.get(0);
        Warehouse warehouse = warehouseService.findByLocation(location);
        if (warehouse == null) {
            throw new Exception("Warehouse at location '" + location + "' does not exist.");
        }
        warehouseService.delete(warehouse);
    }

    @CorporateUser(command = "AddWarehouseInventory", parameterEquation = "p == 3")
    public static void addWarehouseInventory(List<String> args, Corporate corporate) throws Exception {

        String location = args.get(0);
        String type = args.get(1);
        int quantity = Integer.parseInt(args.get(2));

        Warehouse warehouse = warehouseService.findByLocation(location);
        Item item = itemService.findByName(type);

        if (warehouse == null) {
            throw new Exception("Warehouse at location '" + location + "' does not exist.");
        }

        if (item == null) {
            throw new Exception("Item of type '" + type + "' does not exist.");
        }

        WarehouseInventory inventory = new WarehouseInventory(item, item.getSuggestRetailPrice(), quantity);
        inventory.setWarehouse(warehouse);
        warehouseInventoryService.save(inventory);
    }

    @CorporateUser(command = "RemoveWarehouseInventory", parameterEquation = "p == 2")
    public static void removeWarehouseInventory(List<String> args, Corporate corporate) throws Exception {

        String location = args.get(0);
        String type = args.get(1);

        Warehouse warehouse = warehouseService.findByLocation(location);
        Item item = itemService.findByName(type);

        if (warehouse == null) {
            throw new Exception("Warehouse at location '" + location + "' does not exist.");
        }

        if (item == null) {
            throw new Exception("Item of type '" + type + "' does not exist.");
        }

        WarehouseInventory inventory = warehouseInventoryService.findWarehouseInventoriesByWarehouseAndAndItem(warehouse, item);

        if (inventory == null) {
            throw new Exception("Inventory of item '" + type + "' is not present at '" + location + ".");
        }

        warehouseService.removeInventory(warehouse, inventory);
        inventory.setWarehouse(null);
        warehouseInventoryService.delete(inventory);
    }

    //(non-Javadoc)
    // @see gasChain.coreInterfaces.corporate.ICorporateHelper#restockWarehouseInventory(java.lang.String, java.util.List, java.util.List)

    @MethodHelp("Takes name of warehouse as argument along with a list of items and quantities indexed at 0\n" +
            "Iterates through current warehouse database to find matching item. Adds the quantities and saves to database\n" +
            "Returns total cost of the restock(in real world this would already be payed for, might change functionality in that regard later)"
    )
    @CorporateUser(command = "RestockInventory", parameterEquation = "p%2 == 1 && p>0")
    public static int restockWarehouseInventory(List<String> args, Corporate corporate) throws Exception {
        String location = args.get(0);
        Warehouse warehouse = warehouseService.findByLocation(location);
        List<String> items = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();

        for (int idx = 1; idx < args.size() - 1; idx += 2) {
            items.add(args.get(idx));
            quantities.add(Integer.valueOf(args.get(idx + 1)));
        }

        return restockWarehouseInventory2(warehouse, items, quantities);
    }

    public static int restockWarehouseInventory2(Warehouse warehouse, List<String> items, List<Integer> quantities) {
        Set<WarehouseInventory> warehouseInventory = warehouseInventoryService.findByWarehouse(warehouse);
        int total = 0;

        for (int restockIdx = 0; restockIdx < items.size(); restockIdx++) {
            String item = items.get(restockIdx);
            int quantity = quantities.get(restockIdx);
            Iterator<WarehouseInventory> iter = warehouseInventory.iterator();

            while (iter.hasNext()) {
                WarehouseInventory inventoryItem = iter.next();
                if (inventoryItem.ofItem(item)) {
                    total += quantity * inventoryItem.getPrice();
                    inventoryItem.setQuantity(inventoryItem.getQuantity() + quantity);

                    warehouseInventoryService.save(inventoryItem);
                    break;
                }

                // Item isn't in warehouse, add new WarehouseInventory to warehouse
                if (!iter.hasNext()) {
                    Item itemType = itemService.findByName(item);
                    WarehouseInventory newInventory = new WarehouseInventory(itemType,
                            itemType.getSuggestRetailPrice(), quantity);
                    newInventory.setWarehouse(warehouse);
                    warehouse.addInventory(newInventory);
                    warehouseInventoryService.save(newInventory);
                }
            }
        }
        return total;
    }

    private static WarehouseInventory findWarehouseInventory(Set<WarehouseInventory> inventory, Item item) {
        for (WarehouseInventory inventoryItem : inventory) {
            if (inventoryItem.ofItem(item.getName())) {
                return inventoryItem;
            }
        }
        return null;
    }

    @MethodHelp("First enter a location, then an expense name (electric, water, sewage, garbage, insurance) followed by its new cost to update it")
    @CorporateUser(command = "UpdateExpenses", parameterEquation = "p % 2 == 1")
    public static void updateExpenses(List<String> args, Corporate corporate) throws Exception
    {
    	String location = args.get(0);
    	GasStation gasStation = gasStationService.findByLocation(location);

        if (gasStation == null) {
            throw new Exception("Gas station at location does not exist");
        }
        
        Expenses expenses = gasStation.getExpenses();
        
    	int electric = expenses.getElectric(); int water = expenses.getWater(); 
    	int sewage = expenses.getSewage(); int garbage = expenses.getGarbage(); 
    	int insurance = expenses.getSewage();
    	
    	// Loops through args past idx 0, in format TYPE;VALUE
    	for(int idx = 1; idx < args.size() - 1; idx += 2)
    	{
    		String expense = args.get(idx);
    		
    		switch(expense)
    		{
    			case "electric":
    				electric = new Integer(args.get(idx + 1));
    				break;
    			case "water":
    				water = new Integer(args.get(idx + 1));
    				break;
    			case "sewage":
    				sewage = new Integer(args.get(idx + 1));
    				break;
    			case "garbage":
    				garbage = new Integer(args.get(idx + 1));
    				break;
    			case "insurance":
    				insurance = new Integer(args.get(idx + 1));
    				break;
    			default:
    	            throw new Exception("Expense of type '" + expense + "' is invalid.");
    				
    		}
    	}
    	
    	Expenses updateExpenses = new Expenses(electric, water, sewage, garbage, insurance);
    	expenses.update(updateExpenses);
    	gasStationService.save(gasStation);
    }

    @MethodHelp("Enter the name of a file located in the resources/tables folder")
    @CorporateUser(command = "UploadData")
    public static void uploadData(List<String> args, Corporate corporate) throws Exception
    {
    	FileReader.readFile(args.get(0));
    }

    @MethodHelp("Enter the <start date>;<end date> formatted as YYYY-MM-DD")
    @CorporateUser(command = "GenerateFinancialReport")
    public static void generateFinancialReport(List<String> args, Corporate corporateUser){
        List<GasStation> gasStations = gasStationService.findAll();

        Date s = Date.valueOf(args.get(0));
        Date e = Date.valueOf(args.get(1));
        int numDays = Math.abs(s.toLocalDate().getDayOfYear() - e.toLocalDate().getDayOfYear());
        int multiplier = 1;
        if (numDays >= 30)
            multiplier = numDays / 30;

        int electricTotal=0,waterTotal=0,sewageTotal=0,garbageTotal=0,insuranceTotal=0;
        int electricMin=gasStations.get(0).getExpenses().getElectric();
        int waterMin=gasStations.get(0).getExpenses().getWater();
        int sewageMin=gasStations.get(0).getExpenses().getSewage();
        int garbageMin=gasStations.get(0).getExpenses().getGarbage();
        int insuranceMin=gasStations.get(0).getExpenses().getInsurance();
        int electricMax=electricMin, waterMax=waterMin, sewageMax=sewageMin, garbageMax=garbageMin, insuranceMax=insuranceMin;
        String itemizedExpenses = "";
        String electricMinStoreName="",electricMaxStoreName="",waterMinStoreName="",waterMaxStoreName="",sewageMinStoreName="",sewageMaxStoreName="",garbageMinStoreName="",garbageMaxStoreName="",insuranceMinStoreName="",insuranceMaxStoreName="";

        for(GasStation store: gasStations){
            Expenses expenses = store.getExpenses();
            electricTotal+=expenses.getElectric();waterTotal+=expenses.getWater();sewageTotal+=expenses.getSewage();garbageTotal+=expenses.getGarbage();insuranceTotal+=expenses.getInsurance();

            if (expenses.getElectric() < electricMin){
                electricMin = expenses.getElectric();
                electricMinStoreName = store.getLocation();
            }
            if (expenses.getElectric() > electricMax){
                electricMax = expenses.getElectric();
                electricMaxStoreName = store.getLocation();
            }

            if (expenses.getWater() < waterMin){
                waterMin = expenses.getWater();
                waterMinStoreName = store.getLocation();
            }
            if (expenses.getWater() > waterMax){
                waterMax = expenses.getWater();
                waterMaxStoreName = store.getLocation();
            }

            if (expenses.getSewage() < sewageMin){
                sewageMin = expenses.getSewage();
                sewageMinStoreName = store.getLocation();
            }
            if (expenses.getSewage() > sewageMax){
                sewageMax = expenses.getSewage();
                sewageMaxStoreName = store.getLocation();
            }

            if (expenses.getGarbage() < garbageMin){
                garbageMin = expenses.getGarbage();
                garbageMinStoreName = store.getLocation();
            }
            if (expenses.getGarbage() > garbageMax){
                garbageMax = expenses.getGarbage();
                garbageMaxStoreName = store.getLocation();
            }

            if (expenses.getInsurance() < insuranceMin){
                insuranceMin = expenses.getInsurance();
                insuranceMinStoreName = store.getLocation();
            }
            if (expenses.getInsurance() > insuranceMax){
                insuranceMax = expenses.getInsurance();
                insuranceMaxStoreName = store.getLocation();
            }

            itemizedExpenses +=
                    store.getLocation() + " Store Expenses: { Electric: $" + (multiplier * expenses.getElectric()) +
                            ", Water: $" + (multiplier * expenses.getWater()) +
                            ". Sewage: $" + (multiplier * expenses.getSewage()) +
                            ", Garbage: $" + (multiplier * expenses.getGarbage()) +
                            ", Insurance: $" + (multiplier * expenses.getInsurance()) + " }\n";
        }

        double meanElectric = ((double)electricTotal/(double) gasStations.size());
        double meanWater = ((double)waterTotal/(double) gasStations.size());
        double meanSewage = ((double)sewageTotal/(double) gasStations.size());
        double meanGarbage = ((double)garbageTotal/(double) gasStations.size());
        double meanInsurance = ((double)insuranceTotal/(double) gasStations.size());

        System.out.println("GAS STATION CHAIN -EXPENSE REPORT from " + s + "to " + e + ":\n");
        System.out.println("TOTAL EXPENSES BY CATEGORY:");
        System.out.println("Electric: $" + (multiplier * electricTotal)
                        + "\nWater: $" + (multiplier * waterTotal)
                        + "\nSewage: $" + (multiplier * sewageTotal)
                        + "\nGarbage: $" + (multiplier * garbageTotal)
                        + "\nInsurance: $" + (multiplier * insuranceTotal) + "\n");
        System.out.println("LARGEST EXPENSES BY STORE:");
        System.out.println("Electric: $" + (multiplier * electricMax) + "->Store: " + electricMaxStoreName +
                        "\nWater: $" + (multiplier * waterMax) + "->Store: " + waterMaxStoreName +
                        "\nSewage: $" + (multiplier * sewageMax) + "->Store: " + sewageMaxStoreName +
                        "\nGarbage: $" + (multiplier * garbageMax) + "->Store: " + garbageMaxStoreName +
                        "\nInsurance: $" + (multiplier * insuranceMax) + "->Store: " + insuranceMaxStoreName + "\n");
        System.out.println("SMALLEST EXPENSES BY STORE");
        System.out.println("Electric: $" + (multiplier * electricMin) + "->Store: " + electricMinStoreName +
                        "\nWater: $" + (multiplier * waterMin) + "->Store: " + waterMinStoreName +
                        "\nSewage: $" + (multiplier * sewageMin) + "->Store: " + sewageMinStoreName +
                        "\nGarbage: $" + (multiplier * garbageMin) + "->Store: " + garbageMinStoreName +
                        "\nInsurance: $" + (multiplier * insuranceMin) + "->Store: " + insuranceMinStoreName + "\n");
        System.out.println("MEAN EXPENSES BY CATEGORY:");
        System.out.println("Electric: $" + (multiplier * meanElectric) +
                "\nWater: $" + (multiplier * meanWater) +
                "\nSewage: $" + (multiplier * meanSewage) +
                "\nGarbage: $" + (multiplier * meanGarbage) +
                "\nInsurance: $" + (multiplier * meanInsurance) + "\n");
        System.out.println("STD. DEVIATEION OF EXPENSES BY CATEGORY:\n"+ getStandardDeviationExpenses(gasStations, meanElectric, meanWater, meanSewage, meanGarbage, meanInsurance, multiplier));
        System.out.println("ITEMIZED EXPENSES BY STORE:\n"+itemizedExpenses);
    }

    @MethodHelp("Enter the <store_location>;<start date>;<end date> formatted as YYYY-MM-DD")
    @CorporateUser(command = "GenerateFinancialReportByLocation")
    public static void generateFinancialReportByLocation(List<String> args, Corporate corporateUser){
        GasStation store;
        try{
            store = gasStationService.findByLocation(args.get(0));
        }catch(Exception e){
            System.out.println("Unable to find given store location");
            return;
        }
        Date s = Date.valueOf(args.get(1));
        Date e = Date.valueOf(args.get(2));
        int numDays = Math.abs(s.toLocalDate().getDayOfYear() - e.toLocalDate().getDayOfYear());
        int multiplier = 1;
        if (numDays >= 30)
            multiplier = numDays / 30;
        Expenses expenses = store.getExpenses();
        System.out.println(store.getLocation() + " Store Expenses: { Electric: $" + (multiplier * expenses.getElectric()) +
                ", Water: $" + (multiplier * expenses.getWater()) +
                ". Sewage: $" + (multiplier * expenses.getSewage()) +
                ", Garbage: $" + (multiplier * expenses.getGarbage()) +
                ", Insurance: $" + (multiplier * expenses.getInsurance()) + " }\n");
    }

    private static String getStandardDeviationExpenses(List<GasStation> gasStations, double meanElectric, double meanWater, double meanSewage, double meanGarbage, double meanInsurance, int multiplier){
        double totalElectric = 0.0,totalWater = 0.0,totalSewage = 0.0,totalGarbage = 0.0,totalInsurance = 0.0;
        double stdvElectric, stdvWater, stdvSewage, stdvGarbage, stdvInsurance;
        for(GasStation store: gasStations){
            Expenses expenses = store.getExpenses();
            totalElectric += Math.pow(((double)expenses.getElectric())-meanElectric, 2);
            totalWater += Math.pow(((double)expenses.getWater())-meanWater, 2);
            totalGarbage += Math.pow(((double)expenses.getGarbage())-meanGarbage, 2);
            totalSewage += Math.pow(((double)expenses.getSewage())-meanSewage, 2);
            totalInsurance += Math.pow(((double)expenses.getInsurance())-meanInsurance, 2);
        }
        stdvElectric = Math.sqrt(totalElectric)*multiplier;
        stdvWater = Math.sqrt(totalWater)*multiplier;
        stdvSewage = Math.sqrt(totalSewage)*multiplier;
        stdvGarbage = Math.sqrt(totalGarbage)*multiplier;
        stdvInsurance = Math.sqrt(totalInsurance)*multiplier;

        return "Electric: " + stdvElectric +
                "\nWater: " + stdvWater +
                "\nSewage: " + stdvSewage +
                "\nGarbage: " + stdvGarbage +
                "\nInsurance: " + stdvInsurance + "\n";
    }

    @CorporateUser(command = "GenerateSalesReport")
    public static void generateSalesReport(List<String> args, Corporate corporateUser){
        List<Item> sellableItems = itemService.findAll();
        List<Sale> allSales = saleService.findAll();
        List<Sale> firstItemSales = filterSalesByItemId(allSales, sellableItems.get(0).getId());

        int totalRevenue = 0;
        int mostPopularItemCount = firstItemSales.size();
        int leastPopularItemCount = mostPopularItemCount;
        Item mostPopularItem = sellableItems.get(0);
        Item leastPopularItem = sellableItems.get(0);

        int highestItemRevenue = firstItemSales.size()!=0? firstItemSales.size() * firstItemSales.get(0).getPrice(): 0;
        int lowestItemRevenue = highestItemRevenue;
        Item highestRevenueItem = sellableItems.get(0);
        Item lowestRevenueItem = sellableItems.get(0);

        String itemRevenues = "";
        for(Item item: sellableItems){
            List<Sale> itemSales = filterSalesByItemId(allSales, item.getId());
            int totalItemRevenue = itemSales.size()!=0? itemSales.size() * itemSales.get(0).getPrice() : 0;

            if (itemSales.size() > mostPopularItemCount){
                mostPopularItem = item;
                mostPopularItemCount = itemSales.size();
            }
            if (itemSales.size() < leastPopularItemCount){
                leastPopularItem = item;
                leastPopularItemCount = itemSales.size();
            }
            if (totalItemRevenue > highestItemRevenue){
                highestRevenueItem = item;
                highestItemRevenue = totalItemRevenue;
            }
            if (totalItemRevenue < lowestItemRevenue){
                lowestRevenueItem = item;
                lowestItemRevenue = totalItemRevenue;
            }
            itemRevenues += "Item: " + item.getName() + ", Total Revenue: $" + totalItemRevenue +"\n";
            totalRevenue += totalItemRevenue;
        }
        System.out.println("GAS STATION CHAIN -SALES REPORT:\n");
        System.out.println("TOTAL REVENUE: $" + totalRevenue);
        System.out.println("LEAST & MOST POPULAR ITEMS: " + leastPopularItem.getName() + " & " + mostPopularItem.getName());
        System.out.println("LOWEST REVENUE ITEM: " + lowestRevenueItem.getName() + "Generated: $" + lowestItemRevenue);
        System.out.println("HIGHEST REVENUE ITEM: " + highestRevenueItem.getName() + "Generated: $" + highestItemRevenue + "\n");

        for(GasStation store: gasStationService.findAll()){
            List<Sale> storeSales = filterSalesByStoreId(allSales, store.getId());
            List<Sale> firstStoreItemSales = filterSalesByItemId(storeSales, sellableItems.get(0).getId());
            int totalStoreRevenue = 0;

            int highestStoreItemRevenue = firstStoreItemSales.size() !=0? firstStoreItemSales.size() * firstStoreItemSales.get(0).getPrice(): 0;
            int lowestStoreItemRevenue = highestStoreItemRevenue;
            Sale highestRevenueStoreSale = storeSales.get(0);
            Sale lowestRevenueStoreSale = storeSales.get(0);

            int mostPopularStoreItemCount = firstStoreItemSales.size();
            int leastPopularStoreItemCount = mostPopularStoreItemCount;
            Sale mostPopularStoreSale = storeSales.get(0);
            Sale leastPopularStoreSale = storeSales.get(0);

            for(Item item: sellableItems){
                List<Sale> storeItemSales = filterSalesByItemId(storeSales, item.getId());
                int totalStoreItemRevenue = storeItemSales.size()!=0? storeItemSales.size() * storeItemSales.get(0).getPrice(): 0;
                if (storeItemSales.size() > mostPopularStoreItemCount){
                    mostPopularStoreItemCount = storeItemSales.size();
                    mostPopularStoreSale = storeItemSales.get(0);
                }
                if (storeItemSales.size() < leastPopularStoreItemCount && storeItemSales.size()!=0){
                    leastPopularStoreItemCount = storeItemSales.size();
                    leastPopularStoreSale = storeItemSales.get(0);
                }
                if (totalStoreItemRevenue > highestStoreItemRevenue){
                    highestStoreItemRevenue = totalStoreItemRevenue;
                    highestRevenueStoreSale = storeItemSales.get(0);
                }
                if (totalStoreItemRevenue < lowestStoreItemRevenue && storeItemSales.size()!=0){
                    lowestStoreItemRevenue = totalStoreItemRevenue;
                    lowestRevenueStoreSale = storeItemSales.get(0);
                }
                totalStoreRevenue += totalStoreItemRevenue;
            }
            System.out.println("STORE STATISTICS:\n");
            System.out.println("Store: " + store.getLocation() + "Total Revenue: $" + totalStoreRevenue);
            System.out.println("Lowest Item Revenue: " + lowestRevenueStoreSale.getItem().getName() + " Generated: $" + lowestStoreItemRevenue + " At $" + lowestRevenueStoreSale.getPrice() + "/unit");
            System.out.println("Highest Item Revenue: " + highestRevenueStoreSale.getItem().getName() + " Generated: $" + highestStoreItemRevenue + " At $" + highestRevenueStoreSale.getPrice() + "/unit");
            System.out.println("Most Popular Item: " + mostPopularStoreSale.getItem().getName() + " Sold: " + mostPopularStoreItemCount + " units At $" + mostPopularStoreSale.getPrice() + "/units");
            System.out.println("Least Popular Item: " + leastPopularStoreSale.getItem().getName() + " Sold: " + leastPopularStoreItemCount + " units At $" + leastPopularStoreSale.getPrice() + "/unit\n");
        }
    }

    @CorporateUser(command = "GenerateSalesReportByLocation")
    public static void GenerateSalesReportByLocation(List<String> args, Corporate corporateUser){
        Store store;
        try{
            store = gasStationService.findByLocation(args.get(0));
        }catch(Exception e){
            System.out.println("Unable to find given store location");
            return;
        }
        List<Item> sellableItems = itemService.findAll();
        List<Sale> allSales = saleService.findAll();
        List<Sale> storeSales = filterSalesByStoreId(allSales, store.getId());
        List<Sale> firstStoreItemSales = filterSalesByItemId(storeSales, sellableItems.get(0).getId());
        int totalStoreRevenue = 0;

        int highestStoreItemRevenue = firstStoreItemSales.size() !=0? firstStoreItemSales.size() * firstStoreItemSales.get(0).getPrice(): 0;
        int lowestStoreItemRevenue = highestStoreItemRevenue;
        Sale highestRevenueStoreSale = storeSales.get(0);
        Sale lowestRevenueStoreSale = storeSales.get(0);

        int mostPopularStoreItemCount = firstStoreItemSales.size();
        int leastPopularStoreItemCount = mostPopularStoreItemCount;
        Sale mostPopularStoreSale = storeSales.get(0);
        Sale leastPopularStoreSale = storeSales.get(0);

        for(Item item: sellableItems){
            List<Sale> storeItemSales = filterSalesByItemId(storeSales, item.getId());
            int totalStoreItemRevenue = storeItemSales.size()!=0? storeItemSales.size() * storeItemSales.get(0).getPrice(): 0;
            if (storeItemSales.size() > mostPopularStoreItemCount){
                mostPopularStoreItemCount = storeItemSales.size();
                mostPopularStoreSale = storeItemSales.get(0);
            }
            if (storeItemSales.size() < leastPopularStoreItemCount && storeItemSales.size()!=0){
                leastPopularStoreItemCount = storeItemSales.size();
                leastPopularStoreSale = storeItemSales.get(0);
            }
            if (totalStoreItemRevenue > highestStoreItemRevenue){
                highestStoreItemRevenue = totalStoreItemRevenue;
                highestRevenueStoreSale = storeItemSales.get(0);
            }
            if (totalStoreItemRevenue < lowestStoreItemRevenue && storeItemSales.size()!=0){
                lowestStoreItemRevenue = totalStoreItemRevenue;
                lowestRevenueStoreSale = storeItemSales.get(0);
            }
            totalStoreRevenue += totalStoreItemRevenue;
        }
        System.out.println("STORE STATISTICS:\n");
        System.out.println("Store: " + store.getLocation() + "Total Revenue: $" + totalStoreRevenue);
        System.out.println("Lowest Item Revenue: " + lowestRevenueStoreSale.getItem().getName() + " Generated: $" + lowestStoreItemRevenue + " At $" + lowestRevenueStoreSale.getPrice() + "/unit");
        System.out.println("Highest Item Revenue: " + highestRevenueStoreSale.getItem().getName() + " Generated: $" + highestStoreItemRevenue + " At $" + highestRevenueStoreSale.getPrice() + "/unit");
        System.out.println("Most Popular Item: " + mostPopularStoreSale.getItem().getName() + " Sold: " + mostPopularStoreItemCount + " units At $" + mostPopularStoreSale.getPrice() + "/units");
        System.out.println("Least Popular Item: " + leastPopularStoreSale.getItem().getName() + " Sold: " + leastPopularStoreItemCount + " units At $" + leastPopularStoreSale.getPrice() + "/unit\n");
    }


    @CorporateUser(command = "GenerateTaxReport")
    public static void generateTaxReport(List<String> args, Corporate corporateUser) {
        Store store;
        try{
            store = gasStationService.findByLocation(args.get(0));
        }catch(Exception e){
            System.out.println("Unable to find given store location");
            return;
        }
    }

    private static List<Sale> filterSalesByStoreId(List<Sale> sales, long id){
        List<Sale> result = new ArrayList<>();
        for(Sale sale: sales){
            GasStation store = sale.getSellLocation();
            if (store.getId() == id){
                result.add(sale);
            }
        }
        return result;
    }

    private static List<Sale> filterSalesByItemId(List<Sale> sales, long id){
        List<Sale> result = new ArrayList<>();
        for(Sale sale: sales){
            Item item = sale.getItem();
            if(item.getId() == id)
                result.add(sale);
        }
        return result;
    }

    @CorporateUser(command = "GenerateWorkHourReport")
    public static void generateWorkHourReport(List<String> args, Corporate user){
        List<WorkPeriod> workPeriods = workPeriodService.findAll();
        int numWorkPeriods = workPeriods.size();
        int totalHoursWorked = 0;
        int totalWagesPaid = 0;
        Date oldestWorkPeriod = workPeriods.get(0).getDate();
        Date newestWorkPeriod = oldestWorkPeriod;
        for(WorkPeriod workPeriod: workPeriods){
            int shiftHours = workPeriod.getEndHour() - workPeriod.getStartHour();
            totalHoursWorked += shiftHours;
            totalWagesPaid += shiftHours * workPeriod.getWages();
            if (workPeriod.getDate().compareTo(oldestWorkPeriod) < 0)
                oldestWorkPeriod = workPeriod.getDate();
            if (workPeriod.getDate().compareTo(newestWorkPeriod) > 0)
                newestWorkPeriod = workPeriod.getDate();
        }
		int numDays = newestWorkPeriod.toLocalDate().getDayOfYear() - oldestWorkPeriod.toLocalDate().getDayOfYear();
        int totalPayPeriodHours = Math.abs(numDays) * 24;
        System.out.println("WORK HOUR REPORT:\n\n");
        System.out.println("Average Daily Shift: " + (float)totalHoursWorked/(float)numWorkPeriods);
        System.out.println("Scheduling Efficiency: " + (float)totalHoursWorked/(float)totalPayPeriodHours);
        System.out.println("Average Wages Paid: " + (float)totalWagesPaid/(float)numWorkPeriods);
        System.out.println("Total Wages Paid: " + totalWagesPaid);
    }
}
