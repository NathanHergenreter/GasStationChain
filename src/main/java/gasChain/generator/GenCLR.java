package gasChain.generator;

import gasChain.GasStationChainApplication;
import gasChain.entity.*;
import gasChain.service.ServiceMaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class GenCLR implements CommandLineRunner {

	@Autowired
	private ServiceMaster service;

	private GenDataRepository repo;
	
	private Logger LOG = LoggerFactory.getLogger(GasStationChainApplication.class);


	// If the database is empty, generates gas stations and their sales, cashiers,
	// and manager
	@Override
	public void run(String... args) throws Exception {
		if (service.gasStation().isEmpty()) {
			run2();
		}
	}

	private void run2() throws Exception {
		long startTime = System.currentTimeMillis();
		LOG.info("Starting database generation...");

		// Get file-read data
		Path curRelPath = Paths.get("");
		String corePath = curRelPath.toAbsolutePath().toString() + "\\src\\main\\resources\\dbGeneratorData";
		repo = new GenDataRepository(corePath);

		// Generate items
		List<Item> items = repo.items();
		for (Item item : items) {
			service.item().save(item);
		}

		// Generate corporate employees
		int numCorporate = 30;
		LOG.info("Starting generation of corporate employees...");
		generateCorporates(numCorporate, repo.firstNames(), repo.lastNames());

		// Generate warehouses
		LOG.info("Starting generation of warehouses...");
		new WarehouseGenerator(service, repo).execute();

		// Generate gas stations and their employees, sales, and inventory
		LOG.info("Starting generation of gas stations...");
		new GasStationGenerator(service, repo).execute();

		long endTime = System.currentTimeMillis();
		LOG.info("...finished database generation in " + ((endTime - startTime) / 1000) + " seconds");
	}

	private void generateCorporates(int num, ArrayList<String> firstNames, ArrayList<String> lastNames) 
	{
		service.corporate().save(new Corporate("Mr._Monopoly", "password"));
		
		for (int i = 0; i < num; i++) {
			String username = GenUtil.genRandomName(firstNames, lastNames).replace(' ', '_')
					+ (new Integer(GenUtil.rng.nextInt(100))).toString();

			service.corporate().save(new Corporate(username, "password"));
		}
	}
}
