package gasChain.application.manager;

import gasChain.annotation.CorporateUser;
import gasChain.entity.*;
import gasChain.service.*;
import gasChain.util.ServiceAutoWire;

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

    @CorporateUser(command = "AddManager")
    public static void addManager(List<String> args, Corporate corporate) throws Exception {
        System.out.println("Add Manager");
        if (args == null || args.size() != 3) {
            throw new Exception("Invalid number of args for 'AddManager' (3)");
        }

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

    @CorporateUser(command = "RemoveManager")
    public static void removeManager(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 1) {
            throw new Exception("Invalid number of args for 'RemoveManager' (1)");
        }

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

    @CorporateUser(command = "AddGasStation")
    public static void addGasStation(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 3) {
            throw new Exception("Invalid number of args for 'AddGasStation' (3)");
        }

        String location = args.get(0);
        String state = args.get(1);
        String region = args.get(2);

        if (gasStationService.existsLocation(location)) {
            throw new Exception("Gas station at location '" + location + "' already exists.");
        }

        gasStationService.save(new GasStation(location, state, region));
    }

    @CorporateUser(command = "RemoveGasStation")
    public static void removeGasStation(List<String> args, Corporate corporate) throws Exception {
        System.out.println("RemoveGasStation1");
        System.out.println(args);
        if (args == null || args.size() != 1) {
            System.out.println("RemoveGasStation1e");
            throw new Exception("Invalid number of args for 'RemoveGasStation' (1)");
        }
        System.out.println("RemoveGasStation2");

        String location = args.get(0);

        if (!gasStationService.existsLocation(location)) {
            System.out.println("RemoveGasStation2e");
            throw new Exception("Gas station at location '" + location + "' does not exist.");
        }
        System.out.println("RemoveGasStation3");
        GasStation store = gasStationService.findByLocation(location);
        Manager manager = store.getManager();

        if (manager != null) {
            System.out.println("RemoveGasStation4m");
            manager.setStore(null);
        }
        System.out.println("RemoveGasStation5");
        store.setManager(null);

        if (manager != null) {
            System.out.println("RemoveGasStation5m");
            managerService.save(manager);
        }
        System.out.println("RemoveGasStation6");
        gasStationService.delete(store);
        System.out.println("RemoveGasStation7");
    }

    @CorporateUser(command = "AddWareHouse")
    public static void addWarehouse(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 3) {
            throw new Exception("Invalid number of args for 'AddWarehouse' (3)");
        }

        String location = args.get(0);
        String state = args.get(1);
        String region = args.get(2);

        if (warehouseService.existsLocation(location)) {
            throw new Exception("Warehouse at location '" + location + "' already exists.");
        }

        warehouseService.save(new Warehouse(location, state, region));
    }

    @CorporateUser(command = "RemoveWarehouse")
    public static void removeWarehouse(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 1) {
            throw new Exception("Invalid number of args for 'RemoveWarehouse' (1)");
        }

        String location = args.get(0);

        Warehouse warehouse = warehouseService.findByLocation(location);

        if (warehouse == null) {
            throw new Exception("Warehouse at location '" + location + "' does not exist.");
        }

        warehouseService.delete(warehouse);
    }

    @CorporateUser(command = "AddWarehouseInventory")
    public static void addWarehouseInventory(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 3) {
            throw new Exception("Invalid number of args for 'AddWarehouseInventory' (3)");
        }

        String location = args.get(0);
        String type = args.get(1);
        int quantity = new Integer(args.get(2));

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

    @CorporateUser(command = "RemoveWarehouseInventory")
    public static void removeWarehouseInventory(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() != 2) {
            throw new Exception("Invalid number of args for 'RemoveWarehouseInventory' (1)");
        }

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

    /*
     * Takes name of warehouse as argument along with a list of items and quantities indexed at 0
     * Iterates through current warehouse database to find matching item
     * Adds the quantities and saves to database
     * Returns total cost of the restock
     * (in real world this would already be payed for, might change functionality in that regard later)
     *
     * (non-Javadoc)
     * @see gasChain.coreInterfaces.corporate.ICorporateHelper#restockWarehouseInventory(java.lang.String, java.util.List, java.util.List)
     */

    @CorporateUser(command = "RestockInventory")
    public static int restockWarehouseInventory(List<String> args, Corporate corporate) throws Exception {
        if (args == null || args.size() < 1) {
            throw new Exception("Invalid minimum number of args for 'RestockInventory' (1 + 2n)");
        }
        if (args != null && args.size() % 2 == 0) {
            throw new Exception("Invalid number of inventory args for 'RestockInventory' (Must be even)");
        }

        String location = args.get(0);
        Warehouse warehouse = warehouseService.findByLocation(location);
        List<String> items = new ArrayList<String>();
        List<Integer> quantities = new ArrayList<Integer>();

        for (int idx = 1; idx < args.size() - 1; idx += 2) {
            items.add(args.get(idx));
            quantities.add(new Integer(args.get(idx + 1)));
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
}
