package gasChain.application;

import gasChain.scanner.MethodScanner;
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

    public static String getInput() {
        String input = in.nextLine();
        if (input.startsWith("/help")) {
            String[] help = input.split(" ");
            if (help.length > 1) {
                MethodScanner.printHelp(help[1]);
            } else {
                MethodScanner.printCommands();
            }
            return getInput();
        } else if (input.equalsIgnoreCase("exit")) {
            System.exit(0);
        } else if (input.equalsIgnoreCase("logout")) {
            ActiveEmployeeWrapper.userLogout();
        }
        return input;
    }

    @Override
    public void run(String... args) {
        setScanner();
        ActiveEmployeeWrapper.get();	// Need to log in first
        System.out.println("Enter 'exit' to close application or 'logout' to sign off");
        while (true) {
            System.out.println("Enter Command: ");
            String input = getInput();
            try {
                List<String> result = Arrays.asList(input.split(";"));
                MethodScanner.runEmployeeCommand(result);
            } catch (Exception e) {
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
            }
        }
    }

    private void setScanner() {
        in = in == null ? new Scanner(System.in) : in;
    }
}

