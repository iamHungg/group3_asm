package com.carmanagement;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private int response;
    private Scanner scanner = new Scanner(System.in);

    // Phương thức để hiển thị menu và nhận lựa chọn của người dùng
    public <E> int getChoice(ArrayList<E> options) {
        // Hiển thị các lựa chọn
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i)); // In ra danh sách tùy chọn
        }
        System.out.print("Please choose an option 1.." + options.size() + ": ");

        // Kiểm tra tính hợp lệ của nhập liệu
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Tiêu hóa giá trị không hợp lệ
        }

        response = scanner.nextInt();
        scanner.nextLine(); // Tiêu hóa ký tự newline còn sót lại
        if (response < 1 || response > options.size()) {
            System.out.println("Invalid choice. Please choose a valid option.");
            return getChoice(options); // Đệ quy để yêu cầu nhập lại
        }
        return response; // Trả về chỉ số lựa chọn hợp lệ
    }

    // Phương thức chọn một Brand từ BrandList
    public Brand ref_getChoice(BrandList options) {
        System.out.println("Brand ID List:");

        // Lấy lựa chọn của người dùng từ BrandList
        int choice = getChoice(options);

        // Trả về đối tượng Brand tương ứng
        return options.get(choice - 1);
    }
}
