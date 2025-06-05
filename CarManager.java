package Point1;

import java.io.*;
import java.util.*;

// Menu lựa chọn chung cho các danh sách
class Menu<E> {
    // Hiển thị danh sách lựa chọn và trả về số thứ tự người dùng chọn
    public int int_getChoice(ArrayList<E> ds) {
        for (int i = 0; i < ds.size(); i++)
            System.out.println((i + 1) + "- " + ds.get(i));
        System.out.print("Vui long chon (1.." + ds.size() + "): ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                int r = Integer.parseInt(sc.nextLine());
                if (r >= 1 && r <= ds.size()) return r;
                System.out.print("Lua chon khong hop le. Nhap lai: ");
            } catch (Exception e) {
                System.out.print("Dau vao khong phai so. Nhap lai: ");
            }
        }
    }
    // Trả về đối tượng được chọn trong danh sách
    public E ref_getChoice(ArrayList<E> ds) {
        if (ds.isEmpty()) return null;
        return ds.get(int_getChoice(ds) - 1);
    }
}

// Lớp Brand - Thương hiệu xe
class Brand implements Serializable {
    String ma, ten, amthanh; double gia;
    public Brand() {}
    public Brand(String ma, String ten, String amthanh, double gia) {
        this.ma = ma; this.ten = ten; this.amthanh = amthanh; this.gia = gia;
    }
    public String getMa() {return ma;}
    public String getTen() {return ten;}
    public String getAmthanh() {return amthanh;}
    public double getGia() {return gia;}
    public void setMa(String ma) {this.ma = ma;}
    public void setTen(String ten) {this.ten = ten;}
    public void setAmthanh(String amthanh) {this.amthanh = amthanh;}
    public void setGia(double gia) {this.gia = gia;}
    public String toString() {
        return ma + ", " + ten + ", " + amthanh + ": " + String.format("%.3f", gia).replace(",", ".");
    }
    public String toFileString() {
        return ma + ", " + ten + ", " + amthanh + ": " + String.format(Locale.US, "%.3f", gia);
    }
}

// Danh sách thương hiệu
class BrandList extends ArrayList<Brand> {
    private static final String FILE_BRAND = "brands.txt";
    private Scanner sc = new Scanner(System.in);

    // Đọc danh sách thương hiệu từ file
    public boolean loadFromFile(String tenfile) {
        File f = new File(tenfile); if (!f.exists()) return false;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String dong;
            while ((dong = br.readLine()) != null) {
                dong = dong.trim(); if (dong.isEmpty()) continue;
                int c1 = dong.lastIndexOf(',', dong.lastIndexOf(':')), c2 = dong.lastIndexOf(':');
                if (c1 == -1 || c2 == -1) continue;
                String[] idn = dong.substring(0, c1).split(",",2);
                String[] sp = dong.substring(c1+1).split(":",2);
                if (idn.length<2||sp.length<2) continue;
                try {
                    add(new Brand(idn[0].trim(), idn[1].trim(), sp[0].trim(), Double.parseDouble(sp[1].trim().replace(",", "."))));
                } catch (Exception e) {}
            }
            return true;
        } catch (IOException e) {return false;}
    }

    // Ghi danh sách thương hiệu ra file
    public boolean saveToFile(String tenfile) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(tenfile))) {
            for (Brand b : this) pw.println(b.toFileString());
            return true;
        } catch (IOException e) {return false;}
    }

    // Tìm vị trí thương hiệu theo mã
    public int timMa(String ma) {
        for (int i = 0; i < size(); i++)
            if (get(i).getMa().equalsIgnoreCase(ma)) return i;
        return -1;
    }

    // Cho người dùng chọn thương hiệu từ danh sách
    public Brand chonThuongHieu() {
        if (isEmpty()) return null;
        return new Menu<Brand>().ref_getChoice(this);
    }

    // Thêm thương hiệu mới
    public void themThuongHieu() {
        String ma, ten, amthanh; double gia;
        do {System.out.print("Nhap ma thuong hieu: "); ma=sc.nextLine().trim();} while (ma.isEmpty()||timMa(ma)!=-1);
        do {System.out.print("Nhap ten thuong hieu: "); ten=sc.nextLine().trim();} while (ten.isEmpty());
        do {System.out.print("Nhap hang am thanh: "); amthanh=sc.nextLine().trim();} while (amthanh.isEmpty());
        do {System.out.print("Nhap gia: "); try {gia=Double.parseDouble(sc.nextLine().trim());} catch(Exception e){gia=-1;}} while (gia<=0);
        add(new Brand(ma,ten,amthanh,gia)); saveToFile(FILE_BRAND);
    }

    // Cập nhật thương hiệu
    public void capNhatThuongHieu() {
        System.out.print("Nhap ma thuong hieu can cap nhat: "); String ma=sc.nextLine().trim();
        int pos=timMa(ma); if (pos<0) return;
        Brand b=get(pos);
        System.out.print("Ten moi (bo trong giu cu): "); String ten=sc.nextLine().trim(); if(!ten.isEmpty()) b.setTen(ten);
        System.out.print("Am thanh moi (bo trong giu cu): "); String amthanh=sc.nextLine().trim(); if(!amthanh.isEmpty()) b.setAmthanh(amthanh);
        System.out.print("Gia moi (bo trong giu cu): "); String gia=sc.nextLine().trim();
        if(!gia.isEmpty()) try{double d=Double.parseDouble(gia);if(d>0) b.setGia(d);}catch(Exception e){}
        saveToFile(FILE_BRAND);
    }

    // Hiển thị danh sách thương hiệu
    public void hienThiThuongHieu() {
        if (isEmpty()) {System.out.println("Khong co thuong hieu."); return;}
        for (int i=0;i<size();i++) System.out.println((i+1)+". "+get(i));
    }
}

// Lớp Car - Xe
class Car implements Comparable<Car>, Serializable {
    String ma, thuonghieu, mausac, frame, engine;
    Brand brand;
    public Car() {}
    public Car(String ma, Brand brand, String mausac, String frame, String engine) {
        this.ma = ma; this.brand = brand; this.mausac = mausac; this.frame = frame; this.engine = engine;
    }
    public String getMa() {return ma;}
    public Brand getBrand() {return brand;}
    public String getMausac() {return mausac;}
    public String getFrame() {return frame;}
    public String getEngine() {return engine;}
    public void setMa(String ma) {this.ma = ma;}
    public void setBrand(Brand brand) {this.brand = brand;}
    public void setMausac(String mausac) {this.mausac = mausac;}
    public void setFrame(String frame) {this.frame = frame;}
    public void setEngine(String engine) {this.engine = engine;}
    public int compareTo(Car c) {
        int d=brand.getTen().compareTo(c.brand.getTen());
        return d!=0?d:ma.compareTo(c.ma);
    }
    public String toString() {
        return ma + ", " + brand.getMa() + ", " + mausac + ", " + frame + ", " + engine;
    }
    public String hienThi() {
        return brand.getTen() + "\n" + ma + ", " + mausac + ", " + frame + ", " + engine;
    }
}

// Danh sách xe
class CarList extends ArrayList<Car> {
    private static final String FILE_CAR = "cars.txt";
    private BrandList dsThuongHieu;
    private Scanner sc = new Scanner(System.in);

    public CarList(BrandList ds) {dsThuongHieu=ds;}

    // Đọc danh sách xe từ file
    public boolean loadFromFile(String tenfile) {
        File f = new File(tenfile); if (!f.exists()||dsThuongHieu.isEmpty()) return false;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String dong;
            while ((dong = br.readLine()) != null) {
                String[] p = dong.trim().split(",");
                if (p.length==5) {
                    int bpos=dsThuongHieu.timMa(p[1].trim());
                    if (bpos!=-1) add(new Car(p[0].trim(), dsThuongHieu.get(bpos), p[2].trim(), p[3].trim(), p[4].trim()));
                }
            }
            return true;
        } catch (IOException e) {return false;}
    }

    // Ghi danh sách xe ra file
    public boolean saveToFile(String tenfile) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(tenfile))) {
            for (Car c : this) pw.println(c);
            return true;
        } catch (IOException e) {return false;}
    }

    // Tìm vị trí xe theo mã
    public int timMa(String ma) {for(int i=0;i<size();i++)if(get(i).getMa().equalsIgnoreCase(ma))return i;return -1;}
    public int timFrame(String frame) {for(int i=0;i<size();i++)if(get(i).getFrame().equalsIgnoreCase(frame))return i;return -1;}
    public int timEngine(String engine) {for(int i=0;i<size();i++)if(get(i).getEngine().equalsIgnoreCase(engine))return i;return -1;}

    // Thêm xe mới
    public void themXe() {
        if (dsThuongHieu.isEmpty()) return;
        String ma, mausac, frame, engine; Brand b;
        do {System.out.print("Nhap ma xe: "); ma=sc.nextLine().trim();} while (ma.isEmpty()||timMa(ma)!=-1);
        System.out.println("Chon thuong hieu:"); b=dsThuongHieu.chonThuongHieu(); if(b==null)return;
        do {System.out.print("Nhap mau sac: "); mausac=sc.nextLine().trim();} while (mausac.isEmpty());
        do {System.out.print("Nhap Frame ID (F00000): "); frame=sc.nextLine().trim().toUpperCase();} while (!frame.matches("F\\d{5}")||timFrame(frame)!=-1);
        do {System.out.print("Nhap Engine ID (E00000): "); engine=sc.nextLine().trim().toUpperCase();} while (!engine.matches("E\\d{5}")||timEngine(engine)!=-1);
        add(new Car(ma,b,mausac,frame,engine)); saveToFile(FILE_CAR);
    }

    // Hiển thị xe theo một phần tên thương hiệu
    public void hienThiTheoTenThuongHieu() {
        System.out.print("Nhap mot phan ten thuong hieu: "); String s=sc.nextLine().trim().toLowerCase();
        for (Car c : this)
            if (c.getBrand().getTen().toLowerCase().contains(s))
                System.out.println(c.hienThi()+"\n-----");
    }

    // Xóa xe theo mã
    public boolean xoaXe() {
        System.out.print("Nhap ma xe can xoa: "); String ma=sc.nextLine().trim(); int pos=timMa(ma);
        if (pos<0) return false; remove(pos); saveToFile(FILE_CAR); return true;
    }

    // Cập nhật xe
    public boolean capNhatXe() {
        System.out.print("Nhap ma xe can cap nhat: "); String ma=sc.nextLine().trim(); int pos=timMa(ma);
        if (pos<0) return false;
        Car c=get(pos);
        System.out.println("Hien tai:\n"+c.hienThi());
        System.out.println("Chon thuong hieu moi (Enter giu cu):"); Brand b=dsThuongHieu.chonThuongHieu(); if(b==null) b=c.getBrand();
        System.out.print("Mau moi (bo trong giu cu): "); String mausac=sc.nextLine().trim(); if(mausac.isEmpty()) mausac=c.getMausac();
        String frame;
        do {
            System.out.print("Frame ID moi (F00000, bo trong giu cu): "); frame=sc.nextLine().trim().toUpperCase();
            if(frame.isEmpty()) {frame=c.getFrame(); break;}
        } while (!frame.matches("F\\d{5}")||(timFrame(frame)!=-1&&!get(timFrame(frame)).getMa().equalsIgnoreCase(ma)));
        String engine;
        do {
            System.out.print("Engine ID moi (E00000, bo trong giu cu): "); engine=sc.nextLine().trim().toUpperCase();
            if(engine.isEmpty()) {engine=c.getEngine(); break;}
        } while (!engine.matches("E\\d{5}")||(timEngine(engine)!=-1&&!get(timEngine(engine)).getMa().equalsIgnoreCase(ma)));
        c.setBrand(b); c.setMausac(mausac); c.setFrame(frame); c.setEngine(engine); saveToFile(FILE_CAR); return true;
    }

    // Hiển thị danh sách xe (sắp xếp theo tên thương hiệu)
    public void hienThiXe() {
        if (isEmpty()) return;
        Collections.sort(this);
        for (Car c : this) System.out.println(c.hienThi()+"\n--------------------");
    }
}

// Chương trình chính
public class CarManager {
    public static void main(String[] args) {
        ArrayList<String> menu = new ArrayList<>(Arrays.asList(
            "Liet ke tat ca thuong hieu","Them thuong hieu moi","Tim thuong hieu theo ma","Cap nhat thuong hieu",
            "Luu danh sach thuong hieu vao tep","Liet ke tat ca xe (sap xep theo ten thuong hieu)",
            "Liet ke xe theo mot phan ten thuong hieu","Them xe moi","Xoa xe theo ma","Cap nhat xe theo ma",
            "Luu danh sach xe vao tep","Thoat chuong trinh"));
        BrandList dsThuongHieu = new BrandList(); dsThuongHieu.loadFromFile("brands.txt");
        CarList dsXe = new CarList(dsThuongHieu); dsXe.loadFromFile("cars.txt");
        Menu<String> m = new Menu<>(); Scanner sc = new Scanner(System.in); int chon;
        do {
            System.out.println("\n--- MINH TRANG BMW SHOWROOM ---");
            chon = m.int_getChoice(menu);
            switch (chon) {
                case 1: dsThuongHieu.hienThiThuongHieu(); break;
                case 2: dsThuongHieu.themThuongHieu(); break;
                case 3: System.out.print("Nhap ma thuong hieu: "); int pos=dsThuongHieu.timMa(sc.nextLine().trim());
                    System.out.println(pos!=-1?dsThuongHieu.get(pos):"Khong tim thay."); break;
                case 4: dsThuongHieu.capNhatThuongHieu(); break;
                case 5: dsThuongHieu.saveToFile("brands.txt"); break;
                case 6: dsXe.hienThiXe(); break;
                case 7: dsXe.hienThiTheoTenThuongHieu(); break;
                case 8: dsXe.themXe(); break;
                case 9: dsXe.xoaXe(); break;
                case 10: dsXe.capNhatXe(); break;
                case 11: dsXe.saveToFile("cars.txt"); break;
                case 12: System.out.println("Dang thoat..."); break;
            }
            if (chon != menu.size()) {System.out.println("\nNhan Enter de tiep tuc..."); sc.nextLine();}
        } while (chon != menu.size());
        sc.close();
    }
}
