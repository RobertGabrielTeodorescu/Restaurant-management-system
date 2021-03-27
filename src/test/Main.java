package test;

import data.FileWriter;
import data.RestaurantSerializator;
import business.Restaurant;
import presentation.AdministratorGraphicalUserInterface;
import presentation.ChefGraphicalUserInterface;
import presentation.WaiterGraphicalUserInterface;

public class Main {

    public static void main(String[] args) {


        Restaurant restaurant = new Restaurant();
        RestaurantSerializator restaurantSerializator=new RestaurantSerializator(restaurant);
        FileWriter fr=new FileWriter(restaurant);
        restaurantSerializator.load(args[0]);
        ChefGraphicalUserInterface chefGraphicalUserInterface = new ChefGraphicalUserInterface();
        WaiterGraphicalUserInterface waiterGraphicalUserInterface = new WaiterGraphicalUserInterface(restaurant, chefGraphicalUserInterface,fr);
        new AdministratorGraphicalUserInterface(restaurant, waiterGraphicalUserInterface.getSelectItem(),restaurantSerializator);

    }

}

