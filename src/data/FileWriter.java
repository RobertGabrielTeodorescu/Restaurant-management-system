package data;

import business.Order;
import business.Restaurant;

public class FileWriter {

    Restaurant restaurant;

    public FileWriter(Restaurant restaurant){
        this.restaurant=restaurant;
    }

    public void createBillFile(Order order){
        restaurant.generateBill(order);
    }

}
