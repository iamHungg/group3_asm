package caprj;

/**
 *Date:  /2025
 * @author phongtd_HE204117
 *Description: 
 *Version: 1
 */

public class CarManager {
    import java.util.ArrayList;

public class CarManager {
    public static void main(String[] args) {
        // Tạo danh sách lựa chọn menu
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

        // Tạo đối tượng menu
        Menu menu = new Menu();

        // Tạo và nạp danh sách Brand
        BrandList brandList = new BrandList();
        brandList.loadFromFile("brands.txt");

        // Tạo và nạp danh sách Car (cần brandList để khởi tạo Car)
        CarList carList = new CarList(brandList);
        carList.loadFromFile("cars.txt");

        int choice;
        do {
            System.out.println("\n----- MINH TRANG BMW CAR MANAGER -----");
            choice = menu.int_getChoice(ops);

            switch (choice) {
                case 1:
                    brandList.listBrands();
                    break;
                case 2:
                    brandList.addBrand();
                    break;
                case 3:
                    String searchID = Inputter.getString("Enter Brand ID to search: ");
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
                    brandList.saveToFile("brands.txt");
                    System.out.println("Saved to brands.txt");
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
                    carList.saveToFile("cars.txt");
                    System.out.println("Saved to cars.txt");
                    break;
                default:
                    System.out.println("Exit program!");
            }
        } while (choice > 0 && choice <= ops.size());
    }
}
}
