package gasChain.managers;

import gasChain.coreInterfaces.managers.ICashierHelper;
import gasChain.entity.Cashier;
import gasChain.entity.WorkPeriod;
import gasChain.service.GasStationInventoryService;
import gasChain.service.ItemService;
import gasChain.service.ReceiptService;
import gasChain.service.WorkPeriodService;

import java.sql.Date;
import java.util.List;


public class CashierHelper implements ICashierHelper {

    private static CashierHelper cashierHelperSingleton;
    private Cashier _cashier;
    private WorkPeriodService workPeriodService = ManagersAutoWire.getBean(WorkPeriodService.class);
    private ReceiptService receiptService = ManagersAutoWire.getBean(ReceiptService.class);
    private GasStationInventoryService gasStationInventoryService = ManagersAutoWire.getBean(GasStationInventoryService.class);
    private ItemService itemService = ManagersAutoWire.getBean(ItemService.class);

    private CashierHelper(Cashier cashier) {
        _cashier = cashier;
    }

    public static CashierHelper cashierHelper(Cashier cashier) {
        if (cashierHelperSingleton == null) {
            return new CashierHelper(cashier);
        } else {
            return cashierHelperSingleton;
        }
    }



    /*
    expected use case is every employee doesn't add a WorkPeriod until after their shift
    NOTE: given hours should be on a 24 hour basis, also assumed shifts will not be greater than 24 hour period
    args: -startHour -endHour
     */
    @Override
    public void addWorkPeriod(List<String> args) {
        WorkPeriod workPeriod = new WorkPeriod(
                _cashier,
                Integer.parseInt(args.get(0)),
                Integer.parseInt(args.get(1)),
                _cashier.getWagesHourly(),
                new Date(System.currentTimeMillis())
        );
    }

    public void processSale() {

    }
//WIP
//    @Override
//    public void processSale(){
//        Scanner in = new Scanner(System.in);
//
//        Receipt receipt = new Receipt();
//        int total = 0;
//        DecimalFormat df = new DecimalFormat();
//        df.setMaximumFractionDigits(2);
//
//        GasStation gasStation = _cashier.getWorkplace();
//
//        System.out.println("New Order Started With ID: " + receipt.getId());
//        System.out.println("Please proceed to enter item ID");
//        System.out.println("To end transaction enter /end");
//
//        ArrayList<GasStationInventory>  InventoryItems = new ArrayList<>();
//        long id = Long.parseLong(in.nextLine());
//        boolean isEndSale = false;
//        while (!isEndSale){
//            Item item = itemService.findById(id);
//            GasStationInventory gsi = gasStationInventoryService.findGasStationInventoriesByGasStationAndAndItem(gasStation, item);
//            InventoryItems.add(gsi);
//            int price = gsi.getPrice();
//            total += price;
//            receipt.addSale(new Sale(item, gasStation, receipt, price));
//            System.out.println(item.getId() + " --- " + item.getName() + " ---  $" + df.format(price / 100.0) + " --- Total = " +  df.format(total/100.0) );
//
//            String input = in.nextLine();
//            isEndSale = input.equals("/end");
//            id = Long.parseLong(input);
//        }
//        System.out.println("Shopping Cart Total is: " + df.format(total/100.0));
//        System.out.println("How will the customer be paying today?");
//        System.out.println("1 - Credit Card");
//        System.out.println("2 - Debit Card");
//        System.out.println("3 - Cash");
//        int input = in.nextInt();
//        Payment p= processPayment(input, in);
//        if (p == null){
//
//        }
//
//    }
//
//    public Payment processPayment(int i, Scanner in){
//        if (i== 1){
//            System.out.println("Enter Credit Card Number: ");
//            String input = in.nextLine();
//            if (input.equals("/end")){
//                return null;
//            }
//            try{
//                CreditCardAccount cc = new CreditCardAccount(input);
//            } catch (Exception e){
//                System.out.println("Invalid Credit Card");
//                System.out.println("Enter /end to exit or");
//                processPayment(i,in);
//            }
//
//        }
//    }

}
