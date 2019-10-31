package gasChain.managers;

import gasChain.coreInterfaces.managers.IManagerHelper;
import gasChain.entity.Cashier;
import gasChain.entity.Employee;
import gasChain.entity.GasStation;
import gasChain.entity.GasStationInventory;
import gasChain.entity.Item;
import gasChain.entity.Manager;
import gasChain.entity.WarehouseInventory;
import gasChain.service.GasStationInventoryService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import gasChain.service.WarehouseInventoryService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ManagerHelper implements IManagerHelper {
	public ManagerHelper() {

	}

	@Autowired
	GasStationService gasStationService;
	@Autowired
	ManagerService managerService;
	@Autowired
	GasStationInventoryService gasStationInventoryService;
	@Autowired
	WarehouseInventoryService warehouseInventoryService;

	@Override
	public void addCashier(List<String> args) {
		Employee model = new Cashier("", "", "", 0, 0);
		// managerService.add(model);
	}

	@Override
	public void removeCashier(List<String> args) {
		if (managerService.existsUser(args.get(1))) {
			Employee e = managerService.findByUsername(args.get(1));
			// managerService.deleteEmployee(e);
		}
	}

	@Override
	public void updateCashierHours(List<String> args) {

	}

	@Override
	public String getCashierPayroll(List<String> args) {
		if (managerService.existsUser(args.get(1))) {
			Employee e = managerService.findByUsername(args.get(1));
			int hoursWorked = ((Cashier) e).getHoursWeekly();
			float hourlyRate = ((Cashier) e).getWagesHourly();
			float pay = hourlyRate * hoursWorked;
			return Float.toString(pay);
		}
		return null;
	}

	@Override
	public String getEmployeePayrolls(List<String> args) {
		String payrolls = "";
		for (int i = 1; i < args.size(); i++) {
			if (managerService.existsUser(args.get(i))) {
				Employee e = managerService.findByUsername(args.get(i));
				int hoursWorked = ((Cashier) e).getHoursWeekly();
				float hourlyRate = ((Cashier) e).getWagesHourly();
				float pay = hourlyRate * hoursWorked;

				payrolls += "Cashier: " + ((Cashier) e).getName();
				payrolls += "\tPay: " + Float.toString(pay) + "\n";
			}
		}
		return payrolls;
	}

	@Override
	public String getEmployeeSchedule(List<String> args) {
		// TODO: create tables for Availability and Work Period
		return null;
	}

	@Override
	public void addItem(List<String> args) {

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

	@Override
	public boolean restockGasStationInventory(List<String> args) {
		GasStation gasStation = null;
		if (managerService.existsUser(args.get(0))) {
			gasStation = gasStationService.findByManager((Manager) managerService.findByUsername(args.get(0)));
		} else if (gasStationService.existsLocation(args.get(0))) {
			gasStation = gasStationService.findByLocation(args.get(0));
		} else {
			return false;
		}
		Set<GasStationInventory> gasStationInventory = gasStationInventoryService.findByGasStation(gasStation);
		Iterator<GasStationInventory> iter = gasStationInventory.iterator();
		while (iter.hasNext()) {
			GasStationInventory inventory = iter.next();
			if (((float) inventory.getQuantity() / (float) inventory.getMaxQuantity()) <= .5) {
				int desiredQuantity = inventory.getMaxQuantity() - inventory.getQuantity();
				Set<WarehouseInventory> warehouseInventory = inventory.getItem().getInWarehouses();
				Iterator<WarehouseInventory> warehouseIterator = warehouseInventory.iterator();
				while (warehouseIterator.hasNext() && desiredQuantity > 0) {
					WarehouseInventory i = warehouseIterator.next();
					if (inventory.getItem().getName().equals(warehouseIterator.next().getItem().getName())) {
						if (i.getQuantity() >= desiredQuantity) {
							i.setQuantity(i.getQuantity() - desiredQuantity);
							warehouseInventoryService.save(i);
							inventory.setQuantity(inventory.getQuantity() + desiredQuantity);
							gasStationInventoryService.save(inventory);
							desiredQuantity = 0;
						}
						else if (i.getQuantity() < desiredQuantity && i.getQuantity() > 0){
							desiredQuantity -= i.getQuantity();
							inventory.setQuantity(inventory.getQuantity() + i.getQuantity());
							gasStationInventoryService.save(inventory);
							i.setQuantity(0);
							warehouseInventoryService.save(i);
						}
					}
				}
			}
		}
		return true;
	}
}
