package gasChain.managers;

import gasChain.annotation.ManagerUser;
import gasChain.entity.*;
import gasChain.service.*;

import java.sql.Date;
import java.util.*;

public class ManagerHelper {


    private static GasStationService _gasStationService = ManagersAutoWire.getBean(GasStationService.class);
    private static CashierService _cashierService = ManagersAutoWire.getBean(CashierService.class);
    private static ManagerService _managerService = ManagersAutoWire.getBean(ManagerService.class);
    private static WarehouseInventoryService _warehouseInventoryService = ManagersAutoWire.getBean(WarehouseInventoryService.class);
    private static GasStationInventoryService _gasStationInventoryService = ManagersAutoWire.getBean(GasStationInventoryService.class);
    private static WorkPeriodService _workPeriodService = ManagersAutoWire.getBean(WorkPeriodService.class);
    private static AvailabilityService _availabilityService = ManagersAutoWire.getBean(AvailabilityService.class);
    private static ItemService _itemService = ManagersAutoWire.getBean(ItemService.class);



    /*
    args should be ordered: username, password, name, wagesHourly, hoursWeekly
     */
    @ManagerUser(command = "AddCashier")
    public static void addCashier(List<String> args, Manager manager) throws Exception {
        if(args.size()!=5){
            throw new Exception("cmd 'addCashier' does not have the proper number of args (5)");
        }
        Cashier model = new Cashier(
                args.get(0),
                args.get(1),
                args.get(2),
                Integer.parseInt(args.get(3)),
                Integer.parseInt(args.get(4)),
                manager.getStore()
        );
        _cashierService.save(model);
    }

    /*
    args should be ordered: username, password, name, wagesHourly
    NOTE: this method should NOT re-set the username
     */
    @ManagerUser(command = "UpdateCashier")
    public static void updateCashier(List<String> args, Manager manager) throws Exception {
        if (args == null)
            throw new Exception("cmd 'updateCashier' does not have the proper number of args (4)");
        if(args.size()!=4)
            throw new Exception("cmd 'updateCashier' does not have the proper number of args (4)");

        Cashier model = (Cashier) _cashierService.findByUsername(args.get(0));

        model.setPassword(args.get(1));
        model.setName(args.get(2));
        model.setWagesHourly(Integer.parseInt(args.get(3)));

        _cashierService.save(model);
    }

    /*
    args should be given as '-'<username> (' -')-delimited list <startHour>'>'<endHour> or '0' for no available hours
     */
    @ManagerUser(command = "UpdateCashierAvailability")
    public static void updateCashierAvailability(List<String> args, Manager manager) throws Exception {
        if (args == null)
            throw new Exception("cmd 'updateCashierAvailability' does not have the proper number of args (7)");
        if (args.size()!=8)
            throw new Exception("cmd 'updateCashierAvailability' does not have the proper number of args (7)");

        List<List<String>> hours = new ArrayList<List<String>>();
        for(int i=1;i<args.size();i++){
            List<String> nums = Arrays.asList(args.get(i).split(">"));
            hours.add(nums);
        }

        Cashier cashier = (Cashier) _cashierService.findByUsername(args.get(0));
        Availability availability = new Availability(
                cashier,  //cashier to update
                Integer.parseInt(hours.get(0).get(0)), Integer.parseInt(hours.get(0).get(1)),//Sunday hours
                Integer.parseInt(hours.get(1).get(0)), Integer.parseInt(hours.get(1).get(1)),//Monday hours
                Integer.parseInt(hours.get(2).get(0)), Integer.parseInt(hours.get(2).get(1)),//Tuesday hours
                Integer.parseInt(hours.get(3).get(0)), Integer.parseInt(hours.get(3).get(1)),//Wednesday hours
                Integer.parseInt(hours.get(4).get(0)), Integer.parseInt(hours.get(4).get(1)),//Thursday hours
                Integer.parseInt(hours.get(5).get(0)), Integer.parseInt(hours.get(5).get(1)),//Friday hours
                Integer.parseInt(hours.get(6).get(0)), Integer.parseInt(hours.get(6).get(1))//Saturday hours
        );

        cashier.setAvailability(availability);
        _cashierService.save(cashier);
        _availabilityService.save(availability);
    }

    /*
    'username' only required arg
     */
    @ManagerUser(command = "RemoveCashier")
    public static void removeCashier(List<String> args, Manager manager) throws Exception {
        if (_cashierService.existsUser(args.get(0))){
            Cashier e = (Cashier) _cashierService.findByUsername(args.get(0));
            _cashierService.deleteById(e.getId());
        }else{
            throw new Exception("cmd 'removeCashier': given username does not exist");
        }
    }

    private static List<WorkPeriod> getCashierWorkPeriods(Cashier cashier) {
    	List<WorkPeriod> cashierWorkPeriods = new ArrayList<>();
    	List<WorkPeriod> workPeriods = _workPeriodService.findAll();
        for(WorkPeriod period: workPeriods) {
        	long cashierId = cashier.getId();
        	long otherId = period.getCashier().getId();
        	if(cashierId == otherId) {
        		cashierWorkPeriods.add(period);
        	}
        }
        return cashierWorkPeriods;
    }

    private static List<Cashier> getStoreCashiers(GasStation gasStation) {
    	List<Cashier> storeCashiers = new ArrayList<>();
    	List<Cashier> cashiers = _cashierService.findAll();
        for(Cashier cashier: cashiers) {
        	long cashierStoreId = cashier.getWorkplace().getId();
        	long givenStoreId = gasStation.getId();
        	if(cashierStoreId == givenStoreId) {
        		storeCashiers.add(cashier);
        	}
        }
        return storeCashiers;
    }

    @ManagerUser(command = "ListStoreCashiers")
    public static void listStoreCashiers(Manager manager) {
        List<Cashier> cashiers = getStoreCashiers(manager.getStore());
    	for(Cashier cashier: cashiers)
    		System.out.println("Username: " + cashier.getUsername() + " Name: " + cashier.getName() + " Hourly Rate: $" + cashier.getWagesHourly());
    }

    /*
    args should be given as -<username> -<startDate> -<endDate>
     */
    @ManagerUser(command = "GetCashierPayroll")
    public static void getCashierPayroll(List<String> args, Manager manager) throws Exception {
        if (args == null)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (3)");
        if (args.size()!=3)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (3)");

        Cashier cashier = (Cashier)_cashierService.findByUsername(args.get(0));
        Date startDate = Date.valueOf(args.get(1));
        Date endDate = Date.valueOf(args.get(2));

        if (startDate.compareTo(endDate)>=0)
            throw new Exception("cmd 'GetCashierPayroll': end-date must occur after start-date");

        //
        List<WorkPeriod> allWorkPeriods = getCashierWorkPeriods(cashier);
        List<WorkPeriod> workPeriods = new ArrayList<>();
        for(int i=0;i<allWorkPeriods.size();i++){
            if(allWorkPeriods.get(i).getDate().compareTo(startDate) >= 0)
                workPeriods.add(allWorkPeriods.get(i));
        }

        int totalHoursWorked=0;
        for(int i=0;i<workPeriods.size();i++){
            int startTime = workPeriods.get(i).getStartHour();
            int endTime = workPeriods.get(i).getEndHour();
            if (startTime > endTime)
                totalHoursWorked += (24-startTime)+endTime;
            else
                totalHoursWorked += endTime - startTime;
        }

        System.out.println("Employee: "+cashier.getName()+" -Payroll: $"+(totalHoursWorked*cashier.getWagesHourly())+"\n");
    }

    /*
    args: -<startDate> -<endDate>
     */
    @ManagerUser(command = "GetEmployeePayrolls")
    public static void getEmployeePayrolls(List<String> args, Manager manager) throws Exception {
        if (args == null)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (2)");
        if (args.size()!=2)
            throw new Exception("cmd 'GetCashierPayroll': does not have the proper number of args (2)");

        List<Cashier> cashiers = getStoreCashiers(manager.getStore());
        for(int i=0;i<cashiers.size();i++){
            List<String> newArgs = new ArrayList<>();
            newArgs.add(cashiers.get(i).getUsername());
            newArgs.add(args.get(0));
            newArgs.add(args.get(1));
            getCashierPayroll(newArgs, manager);
        }
    }

    /*
    args: -<startDate> -<endDate>
    NOTE: startDate must begin on a sunday
     */
    @ManagerUser(command = "ScheduleEmployees")
    public static void getEmployeeSchedule(List<String> args, Manager manager) {
        Date s = Date.valueOf(args.get(0));
        Date e = Date.valueOf(args.get(1));
        int numDays = Math.abs(s.toLocalDate().getDayOfYear() -e.toLocalDate().getDayOfYear());
        List<Cashier> cashiers = getStoreCashiers(manager.getStore());
        cashiers = getCashiersWithAvailabilities(cashiers);

        String schedule = "Daily Schedule\n\n";
        for(int day=0;day<numDays;day++){
            shuffle(cashiers);
            int availabilityDiff = 0;
            int nextHour = 0;
            schedule += "Day "+(day+1) +":\n";
            while(nextHour<24){
                Cashier nextAvailableCashier = cashiers.get(0);
                availabilityDiff = getNextAvailabilityDiff(day,nextHour,nextAvailableCashier);

                //finds nextAvailableCashier with smallest availability difference
                for(int j=1; j<cashiers.size();j++){
                    Cashier curCashier = cashiers.get(j);
                    int start = getCashierStartOfShift(day%7,curCashier);                    
                    int diff = start - nextHour;
                    if (!(diff<0)) {
                    	if (diff < availabilityDiff || (availabilityDiff<0 && diff>=0)) {
                        	nextAvailableCashier = curCashier;
                        	availabilityDiff = diff;
                        }
                    }else {
                        int end = getCashierEndOfShift(day%7,curCashier);
                        diff = nextHour - end;
                    }
                }

                if(availabilityDiff<0){
                    nextHour = 24;
                }else {
                    int cashierEndOfShift = getCashierEndOfShift(day, nextAvailableCashier);
                    schedule += "Cashier: " + nextAvailableCashier.getName() + "\tShift: " + (nextHour + availabilityDiff) + "-" + cashierEndOfShift+"\n";
                    nextHour += availabilityDiff + cashierEndOfShift;
                }
            }
        }

        System.out.println(schedule);
    }

    private static List<Cashier> getCashiersWithAvailabilities(List<Cashier> cashiers) {
    	List<Cashier> result = new ArrayList<>();
    	for(Cashier cashier: cashiers) {
    		if (cashier.getAvailability()!=null)
    			result.add(cashier);
    	}
    	return result;
    }
    
    // Generic function to randomize a list in Java using Fisher–Yates shuffle
    public static<T> void shuffle(List<T> list)
    {
        Random random = new Random();

        // start from end of the list
        for (int i = list.size() - 1; i >= 1; i--)
        {
            // get a random index j such that 0 <= j <= i
            int j = random.nextInt(i + 1);

            // swap element at i'th position in the list with element at
            // randomly generated index j
            T obj = list.get(i);
            list.set(i, list.get(j));
            list.set(j, obj);
        }
    }

    private static int getNextAvailabilityDiff(int day, int nextHour, Cashier nextAvailableCashier) {
        int availabilityDiff  = 0;
        switch (day%7){
            case 0:
                availabilityDiff = nextAvailableCashier.getAvailability().getSunStart() - nextHour;
                break;
            case 1:
                availabilityDiff = nextAvailableCashier.getAvailability().getMonStart() - nextHour;
                break;
            case 2:
                availabilityDiff = nextAvailableCashier.getAvailability().getTueStart() - nextHour;
                break;
            case 3:
                availabilityDiff = nextAvailableCashier.getAvailability().getWedStart() - nextHour;
                break;
            case 4:
                availabilityDiff = nextAvailableCashier.getAvailability().getThrStart() - nextHour;
                break;
            case 5:
                availabilityDiff = nextAvailableCashier.getAvailability().getFriStart() - nextHour;
                break;
            case 6:
                availabilityDiff = nextAvailableCashier.getAvailability().getSatStart() - nextHour;
                break;
        }
        return availabilityDiff;
    }

    private static int getCashierStartOfShift(int day, Cashier cashier) {
        int endOfShift = 0;
        Availability availability = cashier.getAvailability();
        switch (day%7){
            case 0:
                endOfShift = availability.getSunStart();
                break;
            case 1:
                endOfShift = availability.getMonStart();
                break;
            case 2:
                endOfShift = availability.getTueStart();
                break;
            case 3:
                endOfShift = availability.getWedStart();
                break;
            case 4:
                endOfShift = availability.getThrStart();
                break;
            case 5:
                endOfShift = availability.getFriStart();
                break;
            case 6:
                endOfShift = availability.getSatStart();
                break;
        }
        return endOfShift;
    }

    private static int getCashierEndOfShift(int day, Cashier cashier) {
        int endOfShift = 0;
        Availability availability = cashier.getAvailability();
        switch (day%7){
            case 0:
                endOfShift = availability.getSunEnd();
                break;
            case 1:
                endOfShift = availability.getMonEnd();
                break;
            case 2:
                endOfShift = availability.getTueEnd();
                break;
            case 3:
                endOfShift = availability.getWedEnd();
                break;
            case 4:
                endOfShift = availability.getThrEnd();
                break;
            case 5:
                endOfShift = availability.getFriEnd();
                break;
            case 6:
                endOfShift = availability.getSatEnd();
                break;
        }
        return endOfShift;
    }

    @ManagerUser(command = "TODO")
    public static void addItem(List<String> args, Manager manager) {

	}

    // TODO - check if inventory already contains item
    @ManagerUser(command = "AddGasStationInventory")
    public static void addGasStationInventory(List<String> args, Manager manager) throws Exception
	{
        if (args == null || args.size() != 3)
            throw new Exception("Invalid number of args for 'AddGasStationInventory' (3)");

        String location = manager.getStore().getLocation();
        String type = args.get(0);
        int quantity = new Integer(args.get(1));
        int maxQuantity = new Integer(args.get(2));
        
        GasStation gasStation = _gasStationService.findByLocation(location);
        Item item = _itemService.findByName(type);

        if(gasStation == null)
            throw new Exception("Gas station at location '" + location + "' does not exist.");
        
        if(item == null)
            throw new Exception("Item of type '" + type + "' does not exist.");
        
        GasStationInventory inventory = new GasStationInventory(item, item.getSuggestRetailPrice(), quantity, maxQuantity);
        inventory.setGasStation(gasStation);
//        gasStation.addInventory(inventory);
        _gasStationInventoryService.save(inventory);
//        _gasStationService.save(gasStation);
        
	}

    @ManagerUser(command = "RemoveGasStationInventory")
    public static void removeGasStationInventory(List<String> args, Manager manager) throws Exception {
	}
    
	/*
	 * This beast of a function takes a list of args but the format is such that you're just supposed to supply a location or manager name
	 * If the manager name exists then we find gasStation by manager else if location then by location else return false, ie bad input
	 * From gasStation we check all items to see if quantity is below a tolerance currently set at .5 of maximum amount
	 * Go through as many warehouseInventories we need until will fill that quantity
	 * Save to database accordingly
	 * (Could make this try to search the nearest warehouses first ie. by state / region but this doesn't seem necessary at this point)
	 * (There's a bunch of ways to go about the logic for that but this seems fine as a proof of concept)
	 * 
	 * (non-Javadoc)
	 * @see gasChain.coreInterfaces.managers.IManagerHelper#restockGasStationInventory(java.util.List)
	 */

    @ManagerUser(command = "RestockInventory")
    public static boolean restockGasStationInventory(List<String> args, Manager manager) throws Exception {
//		GasStation gasStation = null;
//		if (_managerService.existsUser(args.get(0))) {
//			gasStation = _gasStationService.findByManager((Manager) _managerService.findByUsername(args.get(0)));
//		} else if (_gasStationService.existsLocation(args.get(0))) {
//			gasStation = _gasStationService.findByLocation(args.get(0));
//		} else {
//			return false;
//		}

        String location = manager.getStore().getLocation();
		GasStation gasStation = _gasStationService.findByLocation(location);

        if(location == null)
            throw new Exception("Gas station at location '" + location + "' does not exist.");
		
		Set<GasStationInventory> gasStationInventory = _gasStationInventoryService.findByGasStation(gasStation);
		Iterator<GasStationInventory> iter = gasStationInventory.iterator();
		while (iter.hasNext()) {
			GasStationInventory inventory = iter.next();
			if (((float) inventory.getQuantity() / (float) inventory.getMaxQuantity()) <= .5) {
				int desiredQuantity = inventory.getMaxQuantity() - inventory.getQuantity();
				//TODO
//				Set<WarehouseInventory> warehouseInventory = inventory.getItem().getInWarehouses();
				Set<WarehouseInventory> warehouseInventory = null;
				Iterator<WarehouseInventory> warehouseIterator = warehouseInventory.iterator();
				while (warehouseIterator.hasNext() && desiredQuantity > 0) {
					WarehouseInventory i = warehouseIterator.next();
					if (inventory.getItem().getName().equals(warehouseIterator.next().getItem().getName())) {
						if (i.getQuantity() >= desiredQuantity) {
							i.setQuantity(i.getQuantity() - desiredQuantity);
							_warehouseInventoryService.save(i);
							inventory.setQuantity(inventory.getQuantity() + desiredQuantity);
							_gasStationInventoryService.save(inventory);
							desiredQuantity = 0;
						}
						else if (i.getQuantity() < desiredQuantity && i.getQuantity() > 0){
							desiredQuantity -= i.getQuantity();
							inventory.setQuantity(inventory.getQuantity() + i.getQuantity());
							_gasStationInventoryService.save(inventory);
							i.setQuantity(0);
							_warehouseInventoryService.save(i);
						}
					}
				}
			}
		}
		return true;
	}
}
