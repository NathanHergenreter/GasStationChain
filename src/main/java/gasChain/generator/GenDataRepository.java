package gasChain.generator;

import gasChain.entity.GasStation;
import gasChain.entity.Item;
import gasChain.entity.Warehouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GenDataRepository {

	private ArrayList<LocationStruct> locations;
	private ArrayList<Item> items;
	private ArrayList<String> firstNames;
	private ArrayList<String> lastNames;


	public GenDataRepository(String corePath) throws FileNotFoundException
	{
		GenUtil.rng = new Random();
		locations = getLocations(new File(corePath + "\\locationNames"));
		items = getItems(new File(corePath + "\\itemTypes"));
		firstNames = getNames(new File(corePath + "\\employeeNamesFirst"));
		lastNames = getNames(new File(corePath + "\\employeeNamesLast"));
	}

	public ArrayList<LocationStruct> locations() {
		return locations;
	}

	public ArrayList<Item> items() {
		return items;
	}

	public ArrayList<String> firstNames() {
		return firstNames;
	}

	public ArrayList<String> lastNames() {
		return lastNames;
	}

	public ArrayList<GasStation> produceGasStations()
	{
		ArrayList<GasStation> gasStations = new ArrayList<GasStation>();
		for(LocationStruct location : locations) {
			gasStations.add(new GasStation(location.location, location.state, location.region));
		}
		return gasStations;
	}

	public ArrayList<Warehouse> produceWarehouses(int num)
	{
		ArrayList<Warehouse> warehouses = new ArrayList<Warehouse>();
		for(int i = 0; i < num; i++) {
			LocationStruct location = locations.get(GenUtil.rng.nextInt(locations.size()));
			warehouses.add(new Warehouse(location.location, location.state, location.region));
		}
		return warehouses;
	}

	// Produces a list names using the data in employeeFirstNames or
	// employeeLastNames
	private ArrayList<String> getNames(File file) throws FileNotFoundException {
		ArrayList<String> names = new ArrayList<String>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt();
		scan.nextLine();

		while (scan.hasNext()) {
			names.add(scan.nextLine());
		}

		scan.close();
		return names;
	}

	// Produces a list of gas stations using the locations in locationNames
	private ArrayList<LocationStruct> getLocations(File file) throws FileNotFoundException {
		ArrayList<LocationStruct> locations = new ArrayList<LocationStruct>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt();
		scan.nextLine();

		while (scan.hasNext()) {
			String[] next = scan.nextLine().split(";");
			switch (version) {
			case 0:
				locations.add(new LocationStruct(next[0], next[1], next[2]));
				break;
			default:
				locations.add(new LocationStruct("Error", "Error", "Error"));
				break;
			}
		}

		scan.close();
		return locations;
	}

	// Produces a list of item templates for sales using the data in itemTypes
	private ArrayList<Item> getItems(File file) throws FileNotFoundException {
		ArrayList<Item> templates = new ArrayList<Item>();
		Scanner scan = new Scanner(file);
		int version = scan.nextInt();
		scan.nextLine();

		while (scan.hasNext()) {
			String[] next = scan.nextLine().split(";");
			switch (version) {
			case 0:
				templates.add(new Item(next[0], Float.parseFloat(next[1])));
				break;
			default:
				templates.add(new Item("Error", -1.00f));
				break;
			}
		}

		scan.close();
		return templates;
	}

	protected class LocationStruct {

		String location;
		String state;
		String region;

		protected LocationStruct(String location, String state, String region) {
			this.location = location;
			this.state = state;
			this.region = region;
		}
	}
}
