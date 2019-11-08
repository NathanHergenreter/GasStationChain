package gasChain.application;

import gasChain.entity.Employee;
import gasChain.scanner.MethodScanner;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
@Order(2)
public class UserApplication implements CommandLineRunner {

    private static Scanner in;

    CashierService cashierService;

    CorporateService corporateService;

    ManagerService managerService;

    @Autowired
    public UserApplication(CashierService cashierService, CorporateService corporateService, ManagerService managerService) {
        this.cashierService = cashierService;
        this.corporateService = corporateService;
        this.managerService = managerService;
        setScanner();
    }

    public static String getInput() {
        Employee employee = ActiveEmployeeWrapper.get();
        String input = in.nextLine();
        if (input.startsWith("/help")) {
            String[] help = input.split(" ");
            if (help.length > 1) {
                MethodScanner.printHelp(help[1]);
            } else {
                MethodScanner.printCommands();
            }
        }
        return input;
    }

    private Scanner setScanner() {
        if (in == null) {
            in = new Scanner(System.in);
        }
        return in;
    }

    @Override
    public void run(String... args) {
        setScanner();
        boolean exit = false;
        Employee employee = ActiveEmployeeWrapper.get(args);
        System.out.println("Enter 'exit' to close application or 'logout' to sign off");
        String input;
        while (!exit) {
            System.out.println("Enter Command: ");
            input = getInput();
            if (input.equals("/help")) {
                MethodScanner.printCommands();
            } else if (input.equals("exit")) {
                exit = true;
            } else {
                try {
                    List<String> result = Arrays.asList(input.split(";"));
                    MethodScanner.runEmployeeCommand(result);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
