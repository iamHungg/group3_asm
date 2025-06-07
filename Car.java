package group3_assignment;

/**
 *
 * @author Phạm Thành Nam - HE204565
 */

import java.io.Serializable; 

public class Car implements Serializable {
    //biến số
    private String carID;
    private Brand brand;
    private String color;
    private String frameID;
    private String engineID;

    
    //constructors
    public Car() {
    }

    public Car(String carID, Brand brand, String color, String frameID, String engineID) {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.frameID = frameID;
        this.engineID = engineID;
    }

    
    //getters
    public String getCarID() {
        return carID;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getFrameID() {
        return frameID;
    }

    public String getEngineID() {
        return engineID;
    }

    
   //setters
    public void setCarID(String carID) {
        this.carID = carID;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFrameID(String frameID) {
        this.frameID = frameID;
    }

    public void setEngineID(String engineID) {
        this.engineID = engineID;
    }
    
    
    //method riêng
    @Override
    public String toString(){
        String s = "<" + carID + ", " + brand + ", " + color  //BrandID???
                 + ", " + frameID + ", " + engineID + ">";
        return s;
    }
    
    public void screenString(){
        System.out.println("<" + brand + ">" + "\n" + carID + ", "
                          + color + ", " + frameID + ", " + engineID + ">");
    }
    
    public int compareTo(Car car){   //cái compareTo() có sẵn trả về int thì method này cũng thế chứ nhỉ
        int temp = brand.getName().compareTo(car.brand.getName());      
        return (temp!=0)? temp : carID.compareTo(car.carID);
    }
}
