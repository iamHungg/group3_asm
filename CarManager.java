package caprj;

/**
 *Date:  /2025
 * @author phongtd_HE204117
 *Description: 
 *Version: 1
 */

import java.util.ArrayList;
import java.util.Scanner;

public class CarManager {
    public static void main(String[] args) {
        ArrayList<String> ops = new ArrayList<>();
        ops.add("List all brands");
        ops.add("Add a new brand");
        ops.add("Search a brand based on its ID");
        ops.add("Update a brand");
        ops.add("Save brands to the file, named brands.txt");
        ops.add("List all cars in ascending order of brand names");
        ops.add("List cars based on a part of an input brand name");
        ops.add("Add a car");
        ops.add("Remove a car based on its ID");
        ops.add("Update a car based on its ID");
        ops.add("Save cars to file, named cars.txt");
        ops.add("Statistics of all brands and cars");
        ops.add("Load data from file again");

        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        BrandList brandList = new BrandList();
        if (!brandList.loadFromFile("brands.txt")) {
            System.out.println("Could not load brands.txt. Exiting.");
            return;
        }

        CarList carList = new CarList(brandList);
        if (!carList.loadFromFile("cars.txt")) {
            System.out.println("Could not load cars.txt. Exiting.");
            return;
        }

        int choice;
        do {
            System.out.println("\n====== MINH TRANG BMW SHOWROOM ======");
            choice = menu.int_getChoice(ops);

            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
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
                    brandList.updateBrand();
                    break;
                case 5:
                    if (brandList.saveToFile("brands.txt")) {
                        System.out.println("Saved to brands.txt successfully.");
                    } else {
                        System.out.println("Failed to save brands.");
                    }
                    break;
                case 6:
                    carList.listCars();
                    break;
                case 7:
                    carList.printBasedBrandName();
                    break;
                case 8:
                    carList.addCar();
                    break;
                case 9:
                    carList.removeCar();
                    break;
                case 10:
                    carList.updateCar();
                    break;
                case 11:
                    if (carList.saveToFile("cars.txt")) {
                        System.out.println("Saved to cars.txt successfully.");
                    } else {
                        System.out.println("Failed to save cars.");
                    }
                    break;
                case 12:
                    carList.statistics();
                    break;
                case 13:
                    brandList.loadFromFile("brands.txt");
                    carList.loadFromFile("cars.txt");
                    System.out.println("Da nap lai du lieu tu file.");
                    break;
                default:
                    System.out.println("Goodbye.");
            }
        } while (choice > 0 && choice <= ops.size());
    }
}