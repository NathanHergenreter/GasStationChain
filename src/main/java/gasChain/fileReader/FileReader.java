package gasChain.fileReader;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import gasChain.entity.Cashier;
import gasChain.entity.GasStation;
import gasChain.entity.Manager;
import gasChain.service.CashierService;
import gasChain.service.GasStationService;
import gasChain.service.ManagerService;
import gasChain.util.ServiceAutoWire;

public class FileReader {

    private static CashierService cashierService = ServiceAutoWire.getBean(CashierService.class);
    private static GasStationService gasStationService = ServiceAutoWire.getBean(GasStationService.class);
    private static ManagerService managerService = ServiceAutoWire.getBean(ManagerService.class);
    
    public static void readFile(String filename) throws Exception
    {
        Path curRelPath = Paths.get("");
        String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\tables\\";
        Scanner in = new Scanner(new File(corePath + filename));
        
        while(in.hasNextLine())
        {
        	String tableType = in.nextLine();
        	
        	switch(tableType)
        	{
        		case "CashierTable":
        			readCashierTable(in);
        			break;

        		case "ManagerTable":
        			readManagerTable(in);
        			break;
        			
    			default:
    				break;
        	}
        }
    }
    
    private static void readCashierTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 6)
                throw new Exception("Invalid cashier table");
            
            String username = row.get(0);
            String password = row.get(1);
            String name = row.get(2);
            int wagesHourly = new Integer(row.get(3));
            int hoursWeekly = new Integer(row.get(4));
            String location = row.get(5);
            GasStation workplace = gasStationService.findByLocation(location);
            
            if(workplace == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");
            
            Cashier cashier = new Cashier(username, password, name, 
        								wagesHourly, hoursWeekly, workplace);
            
        	cashierService.save(cashier);
    	}
    }
    
    private static void readManagerTable(Scanner in) throws Exception
    {
    	for(String line = in.nextLine(); in.hasNextLine() && !line.equals("EndTable"); line = in.nextLine())
    	{
            List<String> row = Arrays.asList(line.split(","));

            if(row.size() != 3)
                throw new Exception("Invalid manager table");
            
            String username = row.get(0);
            String password = row.get(1);
            String location = row.get(2);
            GasStation workplace = gasStationService.findByLocation(location);

            if(workplace == null)
                throw new Exception("Gas station at location '" + location + "' does not exist");
            
            Manager manager = new Manager(username, password);
            manager.setStore(workplace);
            workplace.setManager(manager);
            
        	managerService.save(manager);
        	gasStationService.save(workplace);
    	}
    }
}
