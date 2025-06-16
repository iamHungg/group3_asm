package caprj;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Date: 06/2025
 * @author phongtd_HE204117
 * Description: Main class to run the car management application.
 * Version: 3
 */
public class CarManager {
    public static void main(String[] args) {
        // Create a list of menu options for the user
        ArrayList<String> ops = new ArrayList<>();
        ops.add("List all brands");
        ops.add("Add a new brand");
        ops.add("Search a brand based on its ID");
        ops.add("Update a brand");
        ops.add("Save brands to the file, named brands.txt");
        ops.add("List all cars in ascending order of brand names");
        ops.add("List cars based on a part of an input brand name");
        ops.add("Add a car");
        // Add option to remove a car based on its ID
        ops.add("Remove a car based on its ID");
        ops.add("Update a car based on its ID");
        ops.add("Save cars to file, named cars.txt");
        ops.add("Statistics of all brands and cars");
        ops.add("Load data from file again");

        // Initialize the brand list and load data from file
        BrandList brandList = new BrandList();
        brandList.loadFromFile("brands.txt");

        // Initialize the car list with the brand list and load data from file
        CarList carList = new CarList(brandList);
        carList.loadFromFile("cars.txt");

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            // Display menu and get user choice
            System.out.println("\n====== MINH TRANG BMW SHOWROOM ======");
            Menu menu = new Menu();
            choice = menu.int_getChoice(ops);

            // Handle user choice
            switch (choice) {
                case 1:
                    // List all brands
                    brandList.listBrands();
                    break;
                case 2:
                    // Add a new brand
                    brandList.addBrand();
                    break;
                case 3:
                    // Search for a brand by ID
                    System.out.print("Enter Brand ID to search: ");
                    String searchID = scanner.nextLine().trim();
                    int pos = brandList.searchID(searchID);
                    if (pos < 0) {
                        System.out.println("Not found!");
                    } else {
                        System.out.println(brandList.get(pos).toString());
                    }
                    break;
                case 4:
                    // Update a brand
                    brandList.updateBrand();
                    break;
                case 5:
                    // Save brands to file
                    if (brandList.saveToFile("brands.txt")) {
                        System.out.println("Saved to brands.txt successfully.");
                    } else {
                        System.out.println("Failed to save brands.");
                    }
                    break;
                case 6:
                    // List all cars in ascending order of brand names
                    carList.listCars();
                    break;
                case 7:
                    // List cars based on a part of an input brand name
                    carList.printBasedBrandName();
                    break;
                case 8:
                    // Add a new car
                    carList.addCar();
                    break;
                case 9:
                    // Remove a car based on its ID
                    carList.removeCar();
                    break;
                case 10:
                    // Update a car based on its ID
                    carList.updateCar();
                    break;
                case 11:
                    // Save cars to file
                    if (carList.saveToFile("cars.txt")) {
                        System.out.println("Saved to cars.txt successfully.");
                    } else {
                        System.out.println("Failed to save cars.");
                    }
                    break;
                /*case 12:
                    // Show statistics of all brands and cars
                    carList.statistics();
                    break;
                case 13:
                    // Reload data from files
                    brandList.clear();
                    carList.clear();
                    brandList.loadFromFile("brands.txt");
                    carList.loadFromFile("cars.txt");
                    System.out.println("Da nap lai du lieu tu file.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");*/
            }
        } while (choice > 0 && choice <= ops.size());
        
        // Exit message and close scanner
        System.out.println("Goodbye.");
        scanner.close();
    }
}