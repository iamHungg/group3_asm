/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CarPrj;

/**
 *
 * @author admin
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
                Brand b = brandList.get(pos); //access the actual Brand object at that index
                if (pos >= 0) {
                    add(new Car(parts[0], b, parts[2], parts[3], parts[4])); //add new car to the list 
                }
            }
            return true;
        } catch (IOException e) {
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
            return false;
        }
    }
    
    //search car based on car ID, return existed position
    public int searchID(String carID) { 
        for (int i = 0; i < size(); i++) {
            if (this.get(i).getCarID().equals(carID)) {
                return i; //return car index
            }  
        }
        return -1;
    }
    
    //search car by its frame 
    public int searchFrame(String fID){
        for (int i = 0; i < size(); i++) {
            if (this.get(i).getFrameID().equals(fID)) {
                return i;
            }
        }
        return -1;
    }
    
    //search car by its engine 
    public int searchEngine(String eID){
        for (int i = 0; i < size(); i++) {
            if (this.get(i).getEngineID().equals(eID)) {
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
        } while (searchID(carID) >= 0);
        
        //menu for choosing brand         
        System.out.print("Enter a brand: ");
        Brand b = (Brand) Menu.ref_getChoice(brandList);
        
        //receive color
        do {
            System.out.print("Enter color: ");
            color = input.nextLine();
        } while (color.isEmpty());
        
        //receive frameID
        do {
            System.out.print("Enter frame ID: ");
            frameID = input.nextLine();
        } while (!frameID.matches("F/d{5}") || searchFrame(frameID) >= 0);
 
        //receive engine ID
        do {
            System.out.print("Enter engine ID: ");
            engineID = input.nextLine();
        } while (!engineID.matches("F/d{5}") || searchEngine(engineID) >= 0);

        //add new car to the list 
        add(new Car(carID, b, color, frameID, engineID));
    }
    
    //search car based on part of brand 
    public void printBasedBrandName(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a part of brand name: ");
        String part = input.nextLine();
        int count = 0;
        for (int i = 0; i < this.size(); i++) { //loop through car list 
            Car c = this.get(i);
            if (c.getBrand().getBrandName().contains(part)) {
                System.out.println(c.screenString());
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
            String color, frameID, engineID;
        
            //receive color
            do {
                System.out.print("Enter color: ");
                color = input.nextLine();
            } while (color.isEmpty());
        
            //receive frameID
            do {
                System.out.print("Enter frame ID: ");
                frameID = input.nextLine();
            } while (!frameID.matches("F/d{5}") || searchFrame(frameID) >= 0);
 
            //receive engine ID
            do {
                System.out.print("Enter engine ID: ");
                engineID = input.nextLine();
            } while (!engineID.matches("F/d{5}") || searchEngine(engineID) >= 0);
            
            //update car to carlist
            Car c = new Car(updateID, b, color, frameID, engineID);
            set(pos, c);
            return true;            
        }
    }
    
    public void listCars(){
        Collections.sort(this);
        for (Car c : this) {
            System.out.println(c.screenString());
        }
    }
}
