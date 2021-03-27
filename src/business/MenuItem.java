package business;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {

    private String nume;
    private int price;

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public abstract String toString();
    boolean equals(MenuItem m){
        if(this.getNume().compareTo(m.getNume())==0){
            return true;
        }
        return false;
    }

}
