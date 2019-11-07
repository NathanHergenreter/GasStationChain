package gasChain.managers;

import gasChain.annotation.CashierUser;
import gasChain.entity.*;
import gasChain.service.*;

import java.text.DecimalFormat;
import java.util.*;


public class CashierHelper {

    private static WorkPeriodService workPeriodService = ManagersAutoWire.getBean(WorkPeriodService.class);
    private static ReceiptService receiptService = ManagersAutoWire.getBean(ReceiptService.class);
    private static GasStationInventoryService gasStationInventoryService = ManagersAutoWire.getBean(GasStationInventoryService.class);
    private static ItemService itemService = ManagersAutoWire.getBean(ItemService.class);
    private static CreditCardAccountService creditCardAccountService = ManagersAutoWire.getBean(CreditCardAccountService.class);
    private static DebitAccountService debitAccountService = ManagersAutoWire.getBean(DebitAccountService.class);
    private static CashPaymentService cashPaymentService = ManagersAutoWire.getBean(CashPaymentService.class);
    private static SaleService saleService = ManagersAutoWire.getBean(SaleService.class);
    private static CashierService cashierService = ManagersAutoWire.getBean(CashierService.class);






    /*
    expected use case is every employee doesn't add a WorkPeriod until after their shift
    NOTE: given hours should be on a 24 hour basis, also assumed shifts will not be greater than 24 hour period
    args: -startHour -endHour
     */
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

    @CashierUser(command = "NewSale")
    public static void processSale(List<String> cmd, Cashier cashier) throws Exception {
        Scanner in = new Scanner(System.in);
        Receipt receipt = new Receipt();
        int total = 0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        GasStation gasStation = cashier.getWorkplace();

        System.out.println("New Order Started With ID: " + receipt.getId());
        System.out.println("Please proceed to enter item ID");
        System.out.println("To end transaction enter /end");

        ArrayList<GasStationInventory> InventoryItems = new ArrayList<>();
        long id = Long.parseLong(in.nextLine());
        boolean isEndSale = false;
        while (!isEndSale) {
            Item item = itemService.findById(id);
            System.out.println(item.getId());
            GasStationInventory gsi = gasStationInventoryService.findGasStationInventoryByGasStationAndItem(gasStation, item);
            InventoryItems.add(gsi);
            int price = gsi.getPrice();
            total += price;
            Sale s = new Sale(item, gasStation, receipt, price);
            receipt.addSale(s);
            System.out.println(item.getId() + " --- " + item.getName() + " ---  $" + df.format(price / 100.0) + " --- Total = " + df.format(total / 100.0));

            String input = in.nextLine();
            isEndSale = input.equals("/end");
            if (!isEndSale) {
                id = Long.parseLong(input);
            }
        }
        System.out.println("Shopping Cart Total is: " + df.format(total / 100.0));
        System.out.println("How will the customer be paying today?");
        System.out.println("1 - Credit Card");
        System.out.println("2 - Debit Card");
        System.out.println("3 - Cash");
        int input = Integer.parseInt(getInput(in));
        Payment p = processPayment(input, in, total);

        receipt.setPayment(p);
        for (GasStationInventory i : InventoryItems) {
            gasStationInventoryService.RemoveItemFromInventory(i.getGasStation(), i.getItem());
            gasStationInventoryService.save(i);

        }
        receiptService.save(receipt);
        System.out.println("End of Sale -- Receipt Id: " + receipt.getId());

    }

    private static Payment processPayment(int i, Scanner in, int price) throws Exception {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (i == 1) {
            System.out.println("Enter Credit Card Number: ");
            String input = getInput(in);
            CreditCardAccount cc = creditCardAccountService.findOneByCardNumber(input);
            if (cc == null) {
                try {
                    cc = new CreditCardAccount(input);
                } catch (Exception e) {
                    invalidCard(i, in, price);
                }
            }
            creditCardAccountService.save(cc);
            return cc;
        } else if (i == 2) {
            System.out.println("Enter Debit Card Number: ");
            String input = getInput(in);
            DebitAccount da = debitAccountService.findOneByCardNumber(input);
            if (da == null) {
                try {
                    da = new DebitAccount(input, new Random().nextInt(500000));
                } catch (Exception e) {
                    invalidCard(i, in, price);
                }

            }
            if (da.getAccountBalance() - price >= 0) {
                da.balanceTransaction(-1 * price);
            } else {
                System.out.println("Insufficent Funds- Account Balance: " + da.getAccountBalance());
                System.out.println("Enter /end to exit or");
                processPayment(i, in, price);
            }

            debitAccountService.save(da);
            return da;
        } else if (i == 3) {
            int paymentTotal = 0;
            while (paymentTotal < price) {
                System.out.println("Enter Payment amount: ");
                paymentTotal += Integer.parseInt(getInput(in));
                if (paymentTotal < price) {
                    System.out.println("$" + df.format((price - paymentTotal) / 100) + " Still remaining");
                }
            }
            System.out.println("Total Change: $" + df.format((paymentTotal - price) / 100));
            CashPayment cp = new CashPayment();
            cashPaymentService.save(cp);
            return cp;
        }
        return null;
    }

    private static void invalidCard(int i, Scanner in, int price) throws Exception {
        System.out.println("Invalid Card Number");
        System.out.println("Enter /end to exit or");
        processPayment(i, in, price);
    }

    private static String getInput(Scanner in) throws Exception {
        String input = in.nextLine();
        if (input.equals("/end")) {
            throw new Exception("/endCalled");
        } else return input;
    }

    @CashierUser(command = "ReturnItems")
    public static void processReturn(List<String> args, Cashier cashier) throws Exception {
        if (args.get(0).equals("noReceipt")) {
            Receipt returnReceipt;
            List<Receipt> receipts;
            long item_id;
            if (args.get(1).equals("cc")) {
                CreditCardAccount cc = creditCardAccountService.findOneByCardNumber(args.get(2));
                item_id = Long.parseLong(args.get(3));
                receipts = cc.getTransactionsByItem(item_id);
            } else if (args.get(1).equals("dc")) {
                DebitAccount dc = debitAccountService.findOneByCardNumber(args.get(2));
                item_id = Long.parseLong(args.get(3));
                receipts = dc.getTransactionsByItem(item_id);
            } else {
                System.out.print("Invalid argument given");
                return;
            }
            if (receipts.size() < 1) {
                System.out.println("No Item found with given card number");
                return;
            } else if (receipts.size() == 1) {
                returnReceipt = receipts.get(0);
            } else {
                returnReceipt = receipts.get(0);
                Date date = receipts.get(0).getSales().get(0).getSellDate();
                for (int i = 0; i < receipts.size(); i++) {
                    Date temp_date = receipts.get(i).getSales().get(0).getSellDate();
                    if (temp_date.after(date)) {
                        date = temp_date;
                        returnReceipt = receipts.get(i);
                    }
                }
            }
            for (Sale s : returnReceipt.getSales()) {
                if (s.getItem().getId().equals(item_id)) {
                    returnItem(s, cashier);
                    receiptService.save(receipts.get(0));
                    System.out.println("Item Returned");
                    return;
                }
            }
        } else if (args.get(0).equals("receipt")) {
            Receipt receipt = receiptService.findById(Long.parseLong(args.get(1)));
            long itemId = Long.parseLong(args.get(2));
            for (Sale s : receipt.getSales()) {
                if (s.getItem().getId() == itemId) {
                    returnItem(s, cashier);
                    System.out.println("Item Returned");
                    break;
                }
            }
            System.out.println("Item Returned");
            receiptService.save(receipt);
        } else {
            System.out.println("Please enter valid input: receipt or noReceipt");
        }
    }

    private static void returnItem(Sale sale, Cashier cashier) {
        GasStationInventory inventoryItem =
                gasStationInventoryService.findGasStationInventoryByGasStationAndItem(cashier.getWorkplace(), sale.getItem());
        inventoryItem.setQuantity(inventoryItem.getQuantity() + 1);
        sale.setIsReturned(true);
        gasStationInventoryService.save(inventoryItem);
        saleService.save(sale);
    }
}
