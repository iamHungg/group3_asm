package brandlist;

/* 
* Creator: tk Furry duy nhất trg group
*/
import java.io.*;
import java.util.*;

public class BrandList extends ArrayList<Brand> {
    Scanner scanner = new Scanner(System.in); //nhập dữ liệu từ bàn phím

    //đọc data từ file và thêm các brand vào danh sách
    public boolean loadFromFile(String filename) {
        File file = new File(filename);

        //ktra file có tồn tại không
        if (!file.exists()) {
            System.out.println("File not found.");
            return false;
        }

        try {
            FileReader fr = new FileReader(file); // Mở file để đọc
            BufferedReader br = new BufferedReader(fr); 
            String line;

            while ((line = br.readLine()) != null) {
                // Mỗi dòng có định dạng: ID, tên thương hiệu, âm thanh: giá
                String[] parts = line.split(":");
                if (parts.length != 2) {
                    continue;
                }

                String info = parts[0];                     //phần trc dấu ":"
                String priceStr = parts[1].trim();          //phần sau dấu ":" là giá

                String[] infoParts = info.split(",");
                if (infoParts.length != 3) {
                    continue;
                }

                String id = infoParts[0].trim();
                String name = infoParts[1].trim();
                String sound = infoParts[2].trim();
                double price = Double.parseDouble(priceStr);

                Brand brand = new Brand(id, name, sound, price); // Tạo đối tượng Brand
                this.add(brand);    //thêm vào danh sách
            }

            br.close();
            fr.close();
        } catch (Exception e) {
            System.out.println("Error reading file.");
            return false;
        }

        return true;
    }

    //ghi danh sách brand vào file
    public boolean saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Brand brand : this) {
                pw.println(brand.toString());   //ghi từng dòng theo format của toString()
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    //tìm vị trí của Brand theo ID
    public int searchID(String ID) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getBrandID().equalsIgnoreCase(ID)) {
                return i;       //trả về vị trí
            }
        }
        return -1;      //ko tìm thấy
    }

    //hiển thị danh sách brand 
    public Brand getUserChoice() {
        //danh sách 
        for (int i = 0; i < this.size(); i++) {
            System.out.println((i + 1) + ". " + this.get(i));
        }

        int choice = 0;
        //nhập lựa chọn
        while (choice < 1 || choice > this.size()) {
            System.out.print("Choose a brand (1 to " + this.size() + "): ");
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
            }
        }

        return this.get(choice - 1);    //trả về đối tượng Brand được chọn
    }

    //thêm brand mới vào l
    public void addBrand() {
        String id;
        String name;
        String sound;
        double price = 0;

        //nhập ID không trùng và không trống
        do {
            System.out.print("Enter brand ID: ");
            id = scanner.nextLine().trim();
        } while (searchID(id) != -1 || id.isEmpty());

        //mhập tên thương hiệu
        do {
            System.out.print("Enter brand name: ");
            name = scanner.nextLine().trim();
        } while (name.isEmpty());

        //nhập tên hãng âm thanh
        do {
            System.out.print("Enter sound brand: ");
            sound = scanner.nextLine().trim();
        } while (sound.isEmpty());

        //nhập giá
        do {
            System.out.print("Enter price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                price = 0;
            }
        } while (price <= 0);

        //thêm brand vào list
        this.add(new Brand(id, name, sound, price));
        System.out.println("Brand added successfully.");
    }

    //cap nhật thông tin brand theo ID
    public void updateBrand() {
        String name;
        String sound;
        double price = 0;

        //nhập ID 
        System.out.print("Enter brand ID to update: ");
        String id = scanner.nextLine().trim();
        int pos = searchID(id);

        //không tìm thấy thì báo 
        if (pos < 0) {
            System.out.println("Brand not found!");
            return;
        }

        //nhập tên mới
        do {
            System.out.print("Enter new brand name: ");
            name = scanner.nextLine().trim();
        } while (name.isEmpty());

        //nhập âm thanh mới
        do {
            System.out.print("Enter new sound brand: ");
            sound = scanner.nextLine().trim();
        } while (sound.isEmpty());

        //nhập giá mới
        do {
            System.out.print("Enter new price: ");
            try {
                price = Double.parseDouble(scanner.nextLine());
            } catch (Exception e) {
                price = 0;
            }
        } while (price <= 0);

        //cập nhật thông tin
        Brand brand = this.get(pos);
        brand.setBrandName(name);
        brand.setSoundBrand(sound);
        brand.setPrice(price);
        System.out.println("Brand updated successfully.");
    }

    //in toàn bộ danh sách thương hiệu
    public void listBrands() {
        for (Brand brand : this) {
            System.out.println(brand);
        }
    }
}
