package business;

public class Order {

    private int orderId;
    private String date;
    private int table;

    public Order(int orderId, String date, int table) {
        this.orderId = orderId;
        this.date = date;
        this.table = table;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    public int hashCode() {
        return 2 * (orderId + 2 * (date.length() + 2 * table));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        Order order = (Order) obj;
        return order.getOrderId() == this.getOrderId() && order.getDate().length() == this.getDate().length() && order.getTable() == this.getTable();
    }


}
