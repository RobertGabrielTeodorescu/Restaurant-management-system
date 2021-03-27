package business;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Restaurant implements IRestaurantProcessing {

    private HashMap<Order, ArrayList<MenuItem>> orders = new HashMap<>();
    private ArrayList<MenuItem> menuItems = new ArrayList<>();

    /**
     * functie ce asigura conditia ce trebuie
     * indeplinita la inceputul fiecare functii din aceasta clasa
     * @return
     */
    private boolean invariant() {
        if (orders == null || menuItems == null) {
            return false;
        }
        return true;
    }

    /**
     * functie prin care se va seta lista atunci cand se va da load pentru fisierul restaurant.ser
     * @param menuItems
     */
    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * functia de get pentru lista de produse
     * @return lista curenta de produse
     */
    public ArrayList<MenuItem> getMenuItems() {
        assert invariant();
        return menuItems;
    }

    /**
     * functie ce cauta un produs in lista dupa nume
     * @param name numele produsului pentru care se face cautarea
     * @return obiectul gasit
     */
    public MenuItem findItem(String name) {
        assert invariant();
        for (MenuItem m : menuItems) {
            if (m.getNume().compareTo(name) == 0) {
                return m;
            }
        }
        return null;
    }

    /**
     * functie ce insereaza un produs in lista
     * @param menuItem produsul ce trebuie inserat
     */
    @Override
    public void createNewItem(MenuItem menuItem) {
        assert invariant();
        assert menuItem != null;
        int size = menuItems.size();
        menuItems.add(menuItem);
        assert menuItems.size() == size + 1;
    }

    /**
     * functie ce adauga intr-un composite un produs
     * @param composite produsul composite unde se face inserarea
     * @param product produsul ce se insereaza
     */
    public void addItemtoComposite(CompositeProduct composite, MenuItem product) {
        assert invariant();
        assert composite != null && product != null;
        int size = composite.getItems().size();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.equals(composite)) {
                ((CompositeProduct) menuItem).getItems().add(product);
                ((CompositeProduct) menuItem).computePrice();
            } else {
                if (menuItem instanceof CompositeProduct) {
                    ((CompositeProduct) menuItem).computePrice();
                }
            }
        }
        assert composite.getItems().size() == size + 1;
    }

    /**
     * functie ce realizeaza stergerea unui produs dintr-un composite
     * @param composite produsul composite unde se face stergerea
     * @param product produsul ce se va sterge din lista
     */
    public void removeItemFromComposite(CompositeProduct composite, MenuItem product) {
        assert invariant();
        assert composite != null && product != null;
        int size = composite.getItems().size();
        for (MenuItem menuItem : menuItems) {
            if (menuItem.equals(composite)) {
                int i = 0;
                while (i < ((CompositeProduct) menuItem).getItems().size()) {
                    if (((CompositeProduct) menuItem).getItems().get(i).equals(product)) {
                        ((CompositeProduct) menuItem).getItems().remove(i);
                        break;
                    }
                    i++;
                }
                ((CompositeProduct) menuItem).computePrice();
            } else {
                if (menuItem instanceof CompositeProduct) {
                    ((CompositeProduct) menuItem).computePrice();
                }
            }
        }
        assert composite.getItems().size() == size - 1;
    }

    /**
     * functie ce cauta recursiv un produs intr-un composite
     * @param compositeProduct produsul composite unde s-a ajuns cu cautarea
     * @param menuItem produsul care e cautat
     * @return true daca s-a gasit, false daca nu s-a gasit
     */
    private boolean findItemInComposite(CompositeProduct compositeProduct, MenuItem menuItem) {
        assert invariant();
        assert compositeProduct != null && menuItem != null;
        for (MenuItem m : compositeProduct.getItems()) {
            if (m.equals(menuItem))
                return true;
            else {
                if (m instanceof CompositeProduct) {
                    return findItemInComposite((CompositeProduct) m, menuItem);
                }
            }
        }
        return false;
    }

    /**
     * functie ce realizeaza stergerea unui produs din meniu
     * @param menuItem produsul ce trebuie sters
     */
    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        assert invariant();
        assert menuItems.size() >= 1;
        assert menuItem != null;
        int size = menuItems.size();
        int i = 0;
        while (i < menuItems.size()) {
            if (menuItems.get(i) instanceof CompositeProduct) {
                if (findItemInComposite((CompositeProduct) menuItems.get(i), menuItem)) {
                    menuItems.remove(i);
                } else {
                    i++;
                }
            } else {
                i++;
            }
        }
        i = 0;
        while (i < menuItems.size()) {
            if (menuItems.get(i).equals(menuItem)) {
                menuItems.remove(i);
                break;
            }
            i++;
        }
        assert menuItems.size() < size;
    }


    /**
     * functie ce editeaza un parametru al unui produs
     * @param item produsul ce trebuie editat
     * @param field campul pentru care se face editarea; poate fi numele sau pretul
     * @param newValue noua valoare de introdus
     */
    @Override
    public void editMenuItem(MenuItem item, String field, String newValue) {
        assert invariant();
        assert item != null && newValue != null && field != null;
        assert field.compareTo("name") == 0 || field.compareTo("price") == 0;
        for (MenuItem menuItem : menuItems) {
            if (menuItem.equals(item)) {
                if (field.compareTo("name") == 0) {
                    menuItem.setNume(newValue);
                } else {
                    menuItem.setPrice(Integer.parseInt(newValue));
                }
            }
        }
        for (MenuItem menuItem : menuItems) {
            if (menuItem instanceof CompositeProduct) {
                ((CompositeProduct) menuItem).computePrice();
            }
        }
    }

    /**
     * functie ce adauga o comanda in lista de comenzi
     * @param orderId id ul comenzii
     * @param date data la care s-a facut comanda
     * @param table masa la care s-a facut comanda
     * @param items produsele din comanda
     */
    @Override
    public void createOrder(int orderId, String date, int table, ArrayList<MenuItem> items) {
        assert invariant();
        assert items != null;
        Order order = new Order(orderId, date, table);
        int size = orders.size();
        orders.put(order, items);
        assert orders.size() == size + 1;
    }

    /**
     * functie ce calculeaza nota de plata pentru o comanda
     * @param order comanda pentru care se calculeaza
     * @return se returneaza totatul de plata
     */
    @Override
    public int computePrice(Order order) {
        assert invariant();
        assert order != null;
        int price = 0;
        for (MenuItem m : orders.get(order)) {
            price = price + m.getPrice();
        }
        assert price > 0;
        return price;
    }

    /**
     * functie ce genereaza un fisier text ce contine informatii despre o comanda
     * @param order comanda pentru care se genereaza fisierul
     */
    @Override
    public void generateBill(Order order) {
        assert invariant();
        assert order != null;
        String bill = "";
        bill = bill + "Order nr." + order.getOrderId() + "\n";
        bill = bill + "Date: " + order.getDate() + "\n";
        bill = bill + "Table: " + order.getTable() + "\n";
        for (MenuItem m : orders.get(order)) {
            bill = bill + m.getNume() + "............" + m.getPrice() + " euros\n";
        }
        bill = bill + "Total price to pay.........." + computePrice(order) + " euros";
        try {
            FileWriter fr = new FileWriter("Order" + order.getOrderId() + ".txt");
            fr.write(bill);
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}