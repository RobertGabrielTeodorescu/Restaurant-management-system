package business;

public class BaseProduct extends MenuItem {

    public BaseProduct(String nume,int price){
        super.setNume(nume);
        super.setPrice(price);
    }

    @Override
    public String toString() {
        return super.getNume()+" "+super.getPrice();
    }
}
