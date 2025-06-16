package CarTest;

/* 
* Creator: Hoàng Đức Bình
* Desc: Manages a list of car brands with support for file loading, saving, searching, and updating.
*/
import java.io.*;
import java.util.*;

public class BrandList extends ArrayList<Brand> {
    Scanner scanner = new Scanner(System.in); // nhập dữ liệu từ bàn phím

    // đọc data từ file và thêm các brand vào danh sách
    public boolean loadFromFile(String filename) {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File not found.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    String[] parts = line.split(":");
                    if (parts.length != 2) continue;

                    String info = parts[0];
                    String priceStr = parts[1].trim();

                    String[] infoParts = info.split(",");
                    if (infoParts.length != 3) continue;

                    String id = infoParts[0].trim();
                    String name = infoParts[1].trim();
                    String sound = infoParts[2].trim();
                    double price = Double.parseDouble(priceStr);

                    Brand brand = new Brand(id, name, sound, price);
                    this.add(brand);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid price format in line: " + line);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Invalid data format in line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }

        return true;
    }

    // ghi danh sách brand vào file
    public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Brand brand : this) {
                pw.println(brand.toString());
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    // tìm vị trí của Brand theo ID
    public int searchID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getBrandID().equalsIgnoreCase(ID)) {
                return i;
            }
        }
        return -1;
    }

    // hiển thị danh sách brand và chọn từ người dùng
    public Brand getUserChoice() {
        for (int i = 0; i < this.size(); i++) {
            System.out.println((i + 1) + ". " + this.get(i));
        }

        int choice = 0;
        while (choice < 1 || choice > this.size()) {
            System.out.print("Choose a brand (1 to " + this.size() + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        return this.get(choice - 1);
    }

    // thêm brand mới vào danh sách
    public void addBrand() {
        String id;
        String name;
        String sound;
        double price = 0;

        // nhập ID không trùng và không trống
        do {
            System.out.print("Enter brand ID: ");
            id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                System.out.println("ID cannot be empty.");
            } else if (searchID(id) >= 0) {
                System.out.println("ID already exists.");
                id = "";
            }
        } while (id.isEmpty());

        // nhập tên thương hiệu
        do {
            System.out.print("Enter brand name: ");
            name = scanner.nextLine().trim();
        } while (name.isEmpty());

        // nhập tên hãng âm thanh
        do {
            System.out.print("Enter sound brand: ");
            sound = scanner.nextLine().trim();
        } while (sound.isEmpty());

        // nhập giá
        do {
            System.out.print("Enter price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.out.println("Price must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
                price = 0;
            }
        } while (price <= 0);

        this.add(new Brand(id, name, sound, price));
        System.out.println("Brand added successfully.");
    }

    // cập nhật thông tin brand theo ID
    public void updateBrand() {
        String name;
        String sound;
        double price = 0;

        System.out.print("Enter brand ID to update: ");
        String id = scanner.nextLine().trim();
        int pos = searchID(id);

        if (pos < 0) {
            System.out.println("Brand not found!");
            return;
        }

        do {
            System.out.print("Enter new brand name: ");
            name = scanner.nextLine().trim();
        } while (name.isEmpty());

        do {
            System.out.print("Enter new sound brand: ");
            sound = scanner.nextLine().trim();
        } while (sound.isEmpty());

        do {
            System.out.print("Enter new price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
                if (price <= 0) {
                    System.out.println("Price must be greater than 0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a valid number.");
                price = 0;
            }
        } while (price <= 0);

        Brand brand = this.get(pos);
        brand.setBrandName(name);
        brand.setSoundBrand(sound);
        brand.setPrice(price);
        System.out.println("Brand updated successfully.");
    }

    // in toàn bộ danh sách thương hiệu
    public void listBrands() {
        for (Brand brand : this) {
            System.out.println(brand);
        }
    }
}
