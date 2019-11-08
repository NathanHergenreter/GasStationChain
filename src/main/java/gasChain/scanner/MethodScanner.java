package gasChain.scanner;

import gasChain.annotation.CashierUser;
import gasChain.annotation.CorporateUser;
import gasChain.annotation.ManagerUser;
import gasChain.annotation.MethodHelp;
import gasChain.application.ActiveEmployeeWrapper;
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

    public static Object runEmployeeCommand(List<String> cmd) throws Exception {
        Employee employee = ActiveEmployeeWrapper.get();
        Method m = null;
        String parameterEquation = "";
        List<String> params = cmd.subList(1, cmd.size());
        String command = cmd.get(0).toLowerCase();

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

        if (!checkParams(params, parameterEquation)) {
            System.out.println("Invalid number of arguments given for the command " + command);
        }
        return m != null ? m.invoke(null, params, employee) : null;
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

    public static void printCommands() {
        HashMap<String, Method> hm = getHashMap();
        for (String s : new TreeSet<>(hm.keySet())) {
            System.out.println(s);
        }
    }

    public static void printHelp(String command) {
        HashMap<String, Method> commandHashMap = getHashMap();
        Method m = commandHashMap.get(command);
        MethodHelp annotation = m.getAnnotation(MethodHelp.class);
        if (annotation == null) {
            System.out.println("The " + command + " current doesn't have any help information");
        } else {
            System.out.println("Help for the " + command + " command");
            System.out.println(annotation.value());
        }
    }

    private static HashMap<String, Method> getHashMap() {
        Employee employee = ActiveEmployeeWrapper.get();
        Class ec = employee.getClass();
        if (ec.equals(Cashier.class)) {
            return getCashierMethods();
        } else if (ec.equals(Corporate.class)) {
            return getCorporateMethods();
        } else if (ec.equals(Manager.class)) {
            return getManagerMethods();
        }
        return null;
    }
}




