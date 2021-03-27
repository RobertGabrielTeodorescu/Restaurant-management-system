package business;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {

    private ArrayList<MenuItem> items;

    public CompositeProduct(String nume){
        super.setNume(nume);
        items=new ArrayList<>();
    }

    public ArrayList<MenuItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<MenuItem> items) {
        this.items = items;
    }

    public void computePrice(){
        int price=0;
        for(MenuItem m:items){
            price=price+m.getPrice();
        }
        super.setPrice(price);
    }

    @Override
    public String toString() {
        String s="<html>";
        for(MenuItem m:items){
            s=s+m.getNume()+"<br>";
        }
        s=s+"</html>";
        return s;
    }



}
