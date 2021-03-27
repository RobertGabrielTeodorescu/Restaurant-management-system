package data;

import business.MenuItem;
import business.Restaurant;

import java.io.*;
import java.util.ArrayList;

public class RestaurantSerializator {

    Restaurant restaurant;

    public RestaurantSerializator(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void save() {
        try {
            FileOutputStream fileOut = new FileOutputStream("restaurant.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(restaurant.getMenuItems());
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(String file) {
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            restaurant.setMenuItems((ArrayList<MenuItem>) in.readObject());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
