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
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MethodScanner {

    private static Reflections r = getReflections();

    private static HashMap<String, Method> cashierCommands = getCashierMethods();
    private static HashMap<String, Method> corporateCommands = getCorporateMethods();
    private static HashMap<String, Method> managerCommands = getManagerMethods();

    private static Reflections getReflections() {
        if (r == null) {
            return new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage("gasChain"))
                    .setScanners(new MethodAnnotationsScanner()));
        } else {
            return r;
        }
    }

    private static HashMap<String, Method> getCashierMethods() {
        if (cashierCommands == null) {
            Set<Method> methods = r.getMethodsAnnotatedWith(CashierUser.class);
            HashMap<String, Method> cashierMethodHashMap = new HashMap<>();
            for (Method m : methods) {
                String command = m.getAnnotation(CashierUser.class).command().toLowerCase();
                cashierMethodHashMap.put(command, m);
            }
            return cashierMethodHashMap;
        } else {
            return cashierCommands;
        }
    }

    private static HashMap<String, Method> getCorporateMethods() {
        if (corporateCommands == null) {
            Set<Method> methods = r.getMethodsAnnotatedWith(CorporateUser.class);
            HashMap<String, Method> corporateMethodHashMap = new HashMap<>();
            for (Method m : methods) {
                String command = m.getAnnotation(CorporateUser.class).command().toLowerCase();
                corporateMethodHashMap.put(command, m);
            }
            return corporateMethodHashMap;
        } else {
            return corporateCommands;
        }
    }

    private static HashMap<String, Method> getManagerMethods() {
        if (managerCommands == null) {
            Set<Method> methods = r.getMethodsAnnotatedWith(ManagerUser.class);
            HashMap<String, Method> managerMethodHashMap = new HashMap<>();
            for (Method m : methods) {
                String command = m.getAnnotation(ManagerUser.class).command().toLowerCase();
                managerMethodHashMap.put(command, m);
            }
            return managerMethodHashMap;
        } else {
            return managerCommands;
        }
    }

    public static Object runEmployeeCommand(List<String> cmd, Employee employee) {
        Method m = null;
        String parameterEquation = "";
        List<String> params = cmd.subList(1, cmd.size());
        String command = cmd.get(0).toLowerCase();
        try {
            if (employee.getClass().equals(Cashier.class)) {
                m = cashierCommands.get(command);
                parameterEquation = m.getAnnotation(CashierUser.class).parameterEquation();
            } else if (employee.getClass().equals(Manager.class)) {
                m = managerCommands.get(command);
                parameterEquation = m.getAnnotation(ManagerUser.class).parameterEquation();
            } else if (employee.getClass().equals(Corporate.class)) {
                m = corporateCommands.get(command);
                parameterEquation = m.getAnnotation(CorporateUser.class).parameterEquation();
            }
        } catch (NullPointerException e) {
            System.out.println("Invalid command. Type /help to see available commands");
            return null;
        }
        if (!checkParams(params, parameterEquation)) {
            System.out.println("Invalid number of arguments given for the command " + command);
        }
        try {
            return m.invoke(null, params, employee);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            if (e.getCause() instanceof Exception) {
                System.out.println("Runner: ITE: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Runner Exception E: " + e.getMessage() + " : " + e.toString());
        }
        return null;
    }

    private static boolean checkParams(List<String> params, String parameterEquation) {
        String p = String.valueOf(params.size());
        String equation = parameterEquation.replace(" ", "")
                .replace("p", p)
                .replace("||", "|")
                .replace("&&", "&")
                .toLowerCase();

        ExpressionParser parser = new SpelExpressionParser();
        return (boolean) parser.parseExpression(equation).getValue();
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
        for (String s : new TreeSet<>(hm.keySet())) {
            System.out.println(s);
        }
    }
}




