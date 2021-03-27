package business;

import java.util.ArrayList;

public interface IRestaurantProcessing {

    /**
     * pre conditii: menuItems!=null,menuItem!=null
     * post conditii: size(menuItems) a crescut cu unu
     * @param menuItem
     */
    void createNewItem(MenuItem menuItem);

    /**
     * pre conditii: menuItems!=null, menuItem!=null
     * post conditii: size(menuItems) a scazut
     * @param menuItem
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     * pre conditii: menuItems!=null, item!=null,field=name sau price, newValue!=null
     * @param item
     * @param field
     * @param newValue
     */
    void editMenuItem(MenuItem item,String field,String newValue);
    /**
     * pre conditii: orders!=null, orderId!=null, date!=null, table!=null,items!=null
     * post conditii: size(orders) a crescut cu unu
     * @param orderId
     * @param date
     * @param table
     * @param items
     */
    void createOrder(int orderId, String date, int table, ArrayList<MenuItem>items);

    /**
     * pre conditii: orders!=null,order!=null
     * post conditii: se returneaza un numar mai mare ca 0
     * @param order
     * @return
     */
    int computePrice(Order order);

    /**
     * pre conditii: orders!=null,order!=null
     * @param order
     */
    void generateBill(Order order);

}
