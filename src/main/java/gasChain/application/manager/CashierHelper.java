package gasChain.application.manager;

import gasChain.annotation.CashierUser;
import gasChain.annotation.MethodHelp;
import gasChain.application.UserApplication;
import gasChain.entity.*;
import gasChain.entity.Receipt.Payment;
import gasChain.service.*;
import gasChain.util.Luhn;
import gasChain.util.ServiceAutoWire;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CashierHelper {

    private static WorkPeriodService workPeriodService = ServiceAutoWire.getBean(WorkPeriodService.class);
    private static ReceiptService receiptService = ServiceAutoWire.getBean(ReceiptService.class);
    private static GasStationInventoryService gasStationInventoryService = ServiceAutoWire.getBean(GasStationInventoryService.class);
    private static ItemService itemService = ServiceAutoWire.getBean(ItemService.class);
    private static SaleService saleService = ServiceAutoWire.getBean(SaleService.class);
    private static CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);

    @MethodHelp("expected use case is every employee doesn't add a WorkPeriod until after their shift\n" +
            "    NOTE: given hours should be on a 24 hour basis, also assumed shifts will not be greater than 24 hour period\n" +
            "    args: -startHour -endHour")
    @CashierUser(command = "AddWorkPeriod")
    public static void addWorkPeriod(List<String> args, Cashier cashier) {
        WorkPeriod workPeriod = new WorkPeriod(
                cashier,
                Integer.parseInt(args.get(0)),
                Integer.parseInt(args.get(1)),
                cashier.getWagesHourly(),
                new java.sql.Date(System.currentTimeMillis())
        );

        workPeriodService.save(workPeriod);
        cashierService.save(cashier);
    }

    private static String getInput() throws Exception {
        String input = UserApplication.getInput();
        if (input.equals("/end")) {
            throw new Exception("/endCalled");
        } else {
            return input;
        }
    }

    @CashierUser(command = "NewSale")
    @MethodHelp("NewSale is a one of a kind method will be sure to bring a fun experience to the whole family")
    public static void processSale(List<String> args, Cashier cashier) throws Exception {

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        GasStation gasStation = cashier.getWorkplace();
        Receipt receipt = receiptService.save(new Receipt());

        ArrayList<GasStationInventory> inventoryItems = processSaleGetInventory(gasStation, receipt, df);

        Payment paymentType = processSaleGetPayment(df);
        receipt.setPayment(paymentType);

        for (GasStationInventory inventoryItem : inventoryItems) {
            gasStationInventoryService.RemoveItemFromInventory(inventoryItem.getGasStation(), inventoryItem.getItem());
            gasStationInventoryService.save(inventoryItem);
        }

        System.out.println("End of Sale");
        receiptService.save(receipt);
    }

    private static ArrayList<GasStationInventory> processSaleGetInventory(GasStation gasStation, Receipt receipt, DecimalFormat df) {
        ArrayList<GasStationInventory> inventoryItems = new ArrayList<>();

        System.out.println("New Order Started With ID: " + receipt.getId());
        System.out.println("Please proceed to enter item type");
        System.out.println("To end transaction enter /end");

        int total = 0;
        for (String itemType = UserApplication.getInput(); !itemType.equals("/end"); itemType = UserApplication.getInput()) {
            Item item = itemService.findByName(itemType);
            GasStationInventory inventoryItem = gasStationInventoryService.findGasStationInventoryByGasStationAndItem(gasStation, item);
            inventoryItems.add(inventoryItem);

            int price = inventoryItem.getPrice();
            total += price;

            Sale s = new Sale(item, gasStation, receipt, price);
            receipt.addSale(s);

            System.out.println(item.getId() + " --- " + item.getName() + " ---  $" + df.format(price / 100.0) + " --- Total = " + df.format(total / 100.0));
        }

        return inventoryItems;
    }

    private static Payment processSaleGetPayment(DecimalFormat df) throws Exception {
        Payment paymentType = Receipt.Payment.INVALID;

        boolean invalid = true;
        while (invalid) {
            System.out.println("How will the customer be paying today? (enter /end to end processing)");
            System.out.println("1 - Credit Card");
            System.out.println("2 - Debit Card");
            System.out.println("3 - Cash");

            String input = getInput();

            if (!input.equals("/end")) {
                paymentType = Receipt.Payment.values()[Integer.parseInt(input)];

                invalid = processPayment(paymentType, df);

                if (invalid) {
                    System.out.println("Invalid payment method");
                    paymentType = Receipt.Payment.INVALID;
                }
            }
        }

        return paymentType;
    }

    private static boolean processPayment(Receipt.Payment paymentType, DecimalFormat df) throws Exception {

        boolean continueProcessing = true;
        while (true) {
            switch (paymentType) {
                case CREDIT:
                case DEBIT: {
                    System.out.println("Enter Card Number: ");
                    String cardNumber = getInput();
                    if (!Luhn.validate(cardNumber)) {
                        continueProcessing = invalidCard(cardNumber);
                        if (!continueProcessing) {
                            return false;
                        }
                    } else {
                        return true;
                    }
                    break;
                }
                case CASH: {
                    return true;
                }
                default:
                    return false;
            }
        }
    }

    // Returns true if /end was not entered (i.e. cancel the processing, otherwise retry)
    private static boolean invalidCard(String cardNumber) {
        System.out.println("Invalid Card Number " + cardNumber);
        System.out.println("Enter /end to exit, otherwise to retry");
        return !UserApplication.getInput().equals("/end");
    }

    @CashierUser(command = "ReturnItems", parameterEquation = "p>2")
    public static void processReturn(List<String> args, Cashier cashier) throws Exception {
        Long id = Long.valueOf(args.get(0));
        Receipt saleReceipt = receiptService.findById(id);

        if (saleReceipt == null) {
            throw new Exception("Receipt of id '" + id + "' does not exist.");
        }

        Receipt returnReceipt = receiptService.save(new Receipt());
        for (int i = 1; i < args.size(); i++) {
            String itemType = args.get(i);
            Sale sale = saleReceipt.getSale(itemType);
            if (sale == null) {
                throw new Exception("Item '" + itemType + "' is not on receipt.");
            }
            returnReceipt.addSale(returnItem(new Sale(sale, returnReceipt, -(sale.getPrice())), cashier));
        }
        System.out.print("Items Returned");
        receiptService.save(returnReceipt);
    }

    private static Sale returnItem(Sale sale, Cashier cashier) {
        GasStationInventory inventoryItem =
                gasStationInventoryService.findGasStationInventoryByGasStationAndItem(cashier.getWorkplace(), sale.getItem());
        inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
        gasStationInventoryService.save(inventoryItem);
        saleService.save(sale);

        return sale;
    }
}


