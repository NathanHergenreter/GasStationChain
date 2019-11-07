package gasChain.scanner;

import gasChain.annotation.CashierUser;
import gasChain.annotation.CorporateUser;
import gasChain.annotation.ManagerUser;
import gasChain.entity.Cashier;
import gasChain.entity.Corporate;
import gasChain.entity.Employee;
import gasChain.entity.Manager;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MethodScanner {

    private static Reflections r = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("gasChain"))
            .setScanners(new MethodAnnotationsScanner()));

    private static HashMap<String, Method> cashierCommands = getCashierMethods();
    private static HashMap<String, Method> corporateCommands = getCorporateMethods();
    private static HashMap<String, Method> managerCommands = getManagerMethods();


    private static HashMap<String, Method> getCashierMethods() {
        Set<Method> methods = r.getMethodsAnnotatedWith(CashierUser.class);
        HashMap<String, Method> cashierMethodHashMap = new HashMap<>();
        for (Method m : methods) {
            String command = m.getAnnotation(CashierUser.class).command();
            cashierMethodHashMap.put(command, m);
        }
        return cashierMethodHashMap;
    }

    private static HashMap<String, Method> getCorporateMethods() {
        Set<Method> methods = r.getMethodsAnnotatedWith(CorporateUser.class);
        HashMap<String, Method> corporateMethodHashMap = new HashMap<>();
        for (Method m : methods) {
            String command = m.getAnnotation(CorporateUser.class).command();
            corporateMethodHashMap.put(command, m);
        }
        return corporateMethodHashMap;
    }

    private static HashMap<String, Method> getManagerMethods() {
        Set<Method> methods = r.getMethodsAnnotatedWith(ManagerUser.class);
        HashMap<String, Method> managerMethodHashMap = new HashMap<>();
        for (Method m : methods) {
            String command = m.getAnnotation(ManagerUser.class).command();
            managerMethodHashMap.put(command, m);
        }
        return managerMethodHashMap;
    }

    public static Object runEmployeeCommand(List<String> cmd, gasChain.entity.Employee employee) {
        Method m;
        try {
            if (employee.getClass().equals(Cashier.class)) {
                m = cashierCommands.get(cmd.get(0));
            } else if (employee.getClass().equals(Corporate.class)) {
                m = managerCommands.get(cmd.get(0));
            } else if (employee.getClass().equals(Manager.class)) {
                m = corporateCommands.get(cmd.get(0));
            } else {
                return null;
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid command. Type /help to see list of commands");
            return null;
        }
        try {
            return m.invoke(null, cmd.subList(1, cmd.size()), employee);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                //ignore
            }
        }
        return null;

    }

    public static void printCommands(Employee employee) {
        HashMap<String, Method> hm;
        if (employee.getClass().equals(Cashier.class)) {
            hm = getCashierMethods();
        } else if (employee.getClass().equals(Corporate.class)) {
            hm = getCorporateMethods();
        } else if (employee.getClass().equals(Manager.class)) {
            hm = getManagerMethods();
        } else {
            hm = null;
        }
        for (String s : hm.keySet()) {
            System.out.println(s);
        }
    }
}




