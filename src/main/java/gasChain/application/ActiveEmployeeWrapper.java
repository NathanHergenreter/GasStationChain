package gasChain.application;

import gasChain.annotation.CashierUser;
import gasChain.annotation.CorporateUser;
import gasChain.annotation.ManagerUser;
import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.Employee;
import gasChain.entity.Manager;
import gasChain.service.CashierService;
import gasChain.service.CorporateService;
import gasChain.service.EmployeeService;
import gasChain.service.ManagerService;
import gasChain.util.ServiceAutoWire;

import java.lang.annotation.Annotation;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class ActiveEmployeeWrapper {

    private static CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);
    private static CorporateService corporateService = ServiceAutoWire.getBean(CorporateService.class);
    private static ManagerService managerService = ServiceAutoWire.getBean(ManagerService.class);
    private static EmployeeService e;
    private static Employee activeEmployee;
    private static LocalDateTime loginTime;
    private static HashMap<Class<? extends Employee>, Class<? extends Annotation>> userAnnotationMap;

    public static Class<? extends Annotation> getUserAnnotation() {
        return userAnnotationMap.get(get().getClass());
    }

    public static Employee get(String... args) {
        if (activeEmployee != null) {
            return activeEmployee;
        } else {
            return userLogin(args);
        }
    }

    public static void userLogout() {
        activeEmployee = null;
        Duration duration = Duration.between(LocalDateTime.now(), loginTime);
        System.out.println("User Logged off. Total time logged in: " + duration.toMinutes() + " minutes");
        get();
    }

    private static Employee SetLoggedInEmployee(Employee employee) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        loginTime = LocalDateTime.now();
        System.out.println("New Login on: " + String.format((loginTime.toString()), formatter));
        activeEmployee = employee;
        return get();
    }

    private static void setAnnotationUser() {
        if (userAnnotationMap == null) {
            userAnnotationMap.put(Cashier.class, CashierUser.class);
            userAnnotationMap.put(Manager.class, ManagerUser.class);
            userAnnotationMap.put(Corporate.class, CorporateUser.class);
        }
    }

    private static Employee validateUser(String username, String password) {
        Employee employee = cashierService.findByUsername(username);
        employee = employee != null ? employee : managerService.findByUsername(username);
        employee = employee != null ? employee : corporateService.findByUsername(username);
        return (employee != null && employee.getPassword().equals(password)) ? employee : null;
    }

    private static Employee userLogin(String... args) {
        if (activeEmployee != null) {
            userLogout();
            return null;
        }

        Employee employee = null;
        System.out.println("Enter username: ");
        String username = args.length > 0 ? args[0] : UserApplication.getInput();
        System.out.println("Enter password: ");
        String password = args.length > 1 ? args[1] : UserApplication.getInput();
        while ((employee = validateUser(username, password)) == null) {
            System.out.println("Enter username: ");
            username = UserApplication.getInput();
            System.out.println("Enter password: ");
            password = UserApplication.getInput();
        }
        return SetLoggedInEmployee(employee);
    }
}
