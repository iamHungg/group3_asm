/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CarPrj;

/**
 *
 * @author Pham Vu HE204319
 * date 
 */

import java.io.*;
import java.util.*;

public class CarList extends ArrayList<Car> { //array list: class
    BrandList brandList ;
    
    //initalize a list based on the existed brand list 
    public CarList(BrandList bList) {
        brandList = bList;
    }
    
    //read car data from Car.txt
    public boolean loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) { 
            String line;
            while ((line = br.readLine()) != null){ //read till the end of the line
                String[] parts = line.split(", "); //split line into parts
                int pos = brandList.searchID(parts[1]); //search the brandID in brandList                
                if (pos < 0) {
                    System.out.println("Brand ID not found: " + parts[1]);
                    continue; //skip unknown brand 
                }
                
                Brand b = brandList.get(pos); //access the actual Brand object at that index
                if (b == null) {
                    System.out.println("Brand object is null for ID: " + parts[1]);
                    continue; //
                }
                
                add(new Car(parts[0], b, parts[2], parts[3], parts[4])); //add new car to the list   
                System.out.println("New car added successfully!");
            }
            return true;            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;             
        } catch (Exception e) { 
            System.out.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean saveToFile(String filename){
        try(PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Car c : this) { //this refers to all Car object in CarList
                pw.println(c); //write the car to the file
            }
            return true;
        } catch (IOException e) {
            System.out.println("I/O Error while writing to file: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }
    
    //search car based on car ID, return existed position
    public int searchID(String carID) { 
        if (carID == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            Car car = this.get(i); //create car object to compare 
            if (car != null && car.getCarID().equals(carID)) {
                return i; //return car index
            }  
        }
        return -1;
    }
    
    //search car by its frame 
    public int searchFrame(String fID){
        if (fID == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            Car car = this.get(i); //create car object to compare
            if (car != null && car.getFrameID().equals(fID)) {
                return i;
            }
        }
        return -1;
    }
    
    //search car by its engine 
    public int searchEngine(String eID){
        if (eID == null) {
            return -1;
        }
        for (int i = 0; i < size(); i++) {
            Car car = this.get(i); //create car object to compare
            if (car!= null && car.getEngineID().equals(eID)) {
                return i;
            }
        }
        return -1;
    }
    
    //add new car to the list 
    public void addCar() {
        Scanner input = new Scanner(System.in);
        String carID, color, frameID, engineID;
        
        //receive car ID 
        do {
            System.out.print("Enter car ID: ");
            carID = input.nextLine();
            if (searchID(carID) >= 0){
                System.out.println("This car ID already exists. Please enter another.");
            }
        } while (searchID(carID) >= 0);
        
        //menu for choosing brand         
        System.out.print("Choose a brand: ");
        Brand b = (Brand) Menu.ref_getChoice(brandList);
        
        //receive color
        do {
            System.out.print("Enter color: ");
            color = input.nextLine();
        } while (color.isEmpty());
        
        //receive frameID
        do {
            System.out.print("Enter frame ID (Format: F12345): ");
            frameID = input.nextLine();
            if (!frameID.matches("F\\d{5}")) {
                System.out.println("Invalid format. Must be F followed by 5 digits.");
            } else if (searchFrame(frameID) >= 0) {
                System.out.println("Frame ID already exists.");
            }
        } while (!frameID.matches("F\\d{5}") || searchFrame(frameID) >= 0);
 
        //receive engine ID
        do {
            System.out.print("Enter engine ID (Format: E12345): ");
            engineID = input.nextLine();
            if (!engineID.matches("E\\d{5}")) {
                System.out.println("Invalid format. Must be E followed by 5 digits.");
            } else if (searchEngine(engineID) >= 0) {
                System.out.println("Engine ID already exists.");
            }
        } while (!engineID.matches("E\\d{5}") || searchEngine(engineID) >= 0);

        //add new car to the list 
        add(new Car(carID, b, color, frameID, engineID));
        System.out.println("New car added successfully!");
    }
    
    //search car based on part of brand 
    public void printBasedBrandName(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a part of brand name: ");
        String part = input.nextLine();
        int count = 0;
        for (int i = 0; i < this.size(); i++) { //loop through car list 
            Car c = this.get(i);
            if (c != null && c.getBrand().getBrandName().contains(part)) {
                c.screenString();
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No car is detected!");
        }
    }
    
    public boolean removeCar(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter car ID you want to remove: ");
        String removedID = input.nextLine();
        int pos = searchID(removedID);
        if (pos < 0) {
            System.out.println("Not found!");
            return false;
        } else {
            remove(pos);
            return true;
        }
    }
    
    public boolean updateCar(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter car ID to update: ");
        String updateID = input.nextLine();
        int pos = searchID(updateID);
        if (pos < 0) {
            System.out.println("Not found!");
            return false;
        } else {
            //menu for choosing brand         
            System.out.print("Enter a brand: ");
            Brand b = (Brand) Menu.ref_getChoice(brandList);
            
            String color;        
            //receive color
            do {
                System.out.print("Enter color: ");
                color = input.nextLine();
            } while (color.isEmpty());
        
            String frameID;
            //receive frameID
            do {
                System.out.print("Enter new frame ID (Format: F12345): ");
                frameID = input.nextLine();
                if (!frameID.matches("F\\d{5}")) {
                    System.out.println("Invalid frame ID format.");
                } else if (searchFrame(frameID) >= 0 && !frameID.equals(this.get(pos).getFrameID())) {
                    System.out.println("Frame ID already exists.");
                } else {
                    break;
                }
            } while (true);
 
            String engineID;
            //receive engine ID
            do {
                System.out.print("Enter new engine ID (Format: E12345): ");
                engineID = input.nextLine();
                if (!engineID.matches("E\\d{5}")) {
                    System.out.println("Invalid engine ID format.");
                } else if (searchEngine(engineID) >= 0 && !engineID.equals(this.get(pos).getEngineID())) {
                    System.out.println("Engine ID already exists.");
                } else {
                    break;
                }
            } while (true);
            
            //update car to carlist
            Car c = new Car(updateID, b, color, frameID, engineID);
            set(pos, c);
            return true;            
        }
    }
    
    public void listCars(){
        Collections.sort(this);
        for (Car c : this) {
            c.screenString();
        }
    }
}
