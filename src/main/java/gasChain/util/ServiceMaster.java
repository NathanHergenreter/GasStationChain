package gasChain.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gasChain.service.AvailabilityService;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.GasStationInventoryService;
import gasChain.service.GasStationService;
import gasChain.service.ItemService;
import gasChain.service.ManagerService;
import gasChain.service.ReceiptService;
import gasChain.service.SaleService;
import gasChain.service.WarehouseInventoryService;
import gasChain.service.WarehouseService;
import gasChain.service.WorkPeriodService;

@Service
public class ServiceMaster {

	@Autowired
	private AvailabilityService availabilityService;

	@Autowired
	private CashierService cashierService;

	@Autowired
	private CorporateService corporateService;

	@Autowired
	private GasStationInventoryService gasStationInventoryService;

	@Autowired
	private GasStationService gasStationService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private ReceiptService receiptService;

	@Autowired
	private SaleService saleService;

	@Autowired
	private WarehouseInventoryService warehouseInventoryService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private WorkPeriodService workPeriodService;
	
	public AvailabilityService availability() { return availabilityService; }
	public CashierService cashier() { return cashierService; }
	public CorporateService corporate() { return corporateService; }
	public ItemService item() { return itemService; }
	public GasStationInventoryService gasStationInventory() { return gasStationInventoryService; }
	public GasStationService gasStation() { return gasStationService; }
	public ManagerService manager() { return managerService; }
	public ReceiptService receipt() { return receiptService; }
	public SaleService sale() { return saleService; }
	public WarehouseInventoryService warehouseInventory() { return warehouseInventoryService; }
	public WarehouseService warehouse() { return warehouseService; }
	public WorkPeriodService workPeriod() { return workPeriodService; }
}