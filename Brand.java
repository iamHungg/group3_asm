
package project;

/**
 * Date: 5/6/2025
 * @author Nguyen Minh Nhat, Ma SV: HE204228
 * Nội dung: 
 * - Lớp Brand đại diện cho một thương hiệu xe BMW. 
 * - Lưu trữ thông tin cơ bản của thương hiệu và cung cấp các phương thức truy cập/thay đổi.
 */

import java.io.Serializable; // Cho phép đối tượng Brand được tuần tự hóa (lưu/đọc từ file)

public class Brand implements Serializable {

    // Thuộc tính của lớp Brand
    // Khai báo private
    private String brandID;      // Mã định danh duy nhất của thương hiệu
    private String brandName;    // Tên đầy đủ của thương hiệu
    private String soundBrand;   // Tên thương hiệu hệ thống âm thanh
    private double price;        // Giá của xe
    
    /**
     * Constructor mặc định.
     * Khởi tạo một đối tượng Brand với các giá trị mặc định (null cho String, 0.0 cho double).
     */
    public Brand() {
    }
    /**
     * Constructor đầy đủ tham số.
     * Khởi tạo một đối tượng Brand với tất cả các thông tin được cung cấp.
     */
    public Brand(String brandID, String brandName, String soundBrand, double price) {
        this.brandID = brandID;         // Mã định danh
        this.brandName = brandName;     // Tên thương hiệu
        this.soundBrand = soundBrand;   // Tên thương hiệu hệ thống âm thanh
        this.price = price;             // Giá xe
    }
    
    // --- Getters và Setters ---
    // Cung cấp quyền truy cập và thay đổi các thuộc tính private.

    public String getBrandID() {
        return brandID;                 // Trả về giá trị của mã định danh
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;         // Gán giá trị cho mã định danh
    }

    public String getBrandName() {
        return brandName;               // Trả về giá trị của tên thương hiệu
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;     // Gán giá trị cho tên thương hiệu
    }

    public String getSoundBrand() {
        return soundBrand;              // Trả về giá trị của tên thương hiệu của hệ thống âm thanh
    }

    public void setSoundBrand(String soundBrand) {
        this.soundBrand = soundBrand;   // Gán giá trị cho tên thương hiệu của hệ thống âm thanh
    }

    public double getPrice() {
        return price;                   // Trả về giá trị của giá xe
    }

    public void setPrice(double price) {
        this.price = price;             // Gán giá trị cho giá xe
    }

    // --- Phương thức toString() ---

    /**
     * Ghi đè phương thức toString() để trả về thông tin Brand theo định dạng yêu cầu.
     * Định dạng: <brandID, brandName, soundBrand: price>
     * @return Chuỗi biểu diễn của đối tượng Brand.
     */
    @Override
    public String toString() {
        return brandID + ", " + brandName + ", " + soundBrand + ": " + price;
    }
}
