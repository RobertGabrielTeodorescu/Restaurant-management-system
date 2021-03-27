package presentation;

import business.CompositeProduct;
import business.MenuItem;
import business.Order;
import business.Restaurant;
import data.FileWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class WaiterGraphicalUserInterface {

    private JLabel orderIdCreate = new JLabel("Order nr.");
    private JTextField orderIdCreateText = new JTextField(4);
    private JLabel date = new JLabel("Date");
    private JTextField dateText = new JTextField(10);
    private JLabel table = new JLabel("Table");
    private JTextField tableText = new JTextField(4);
    private JLabel select = new JLabel("Select items");
    private JComboBox selectItem = new JComboBox();
    private JButton addItems = new JButton("Add item");
    private JTextArea selectedItemsText = new JTextArea(10, 10);
    private JButton create = new JButton("Create order");
    private JLabel orderNrCompute = new JLabel("Order nr.");
    private JTextField orderNrComputeText = new JTextField(4);
    private JLabel dateCompute = new JLabel("Date");
    private JTextField dateComputeText = new JTextField(10);
    private JLabel tableCompute = new JLabel("Table");
    private JTextField tableComputeText = new JTextField(4);
    private JButton compute = new JButton("Compute");
    private JLabel price = new JLabel("Price: ");
    private JLabel orderNrBill = new JLabel("Order nr.");
    private JTextField orderNrBillText = new JTextField(4);
    private JLabel dateBill = new JLabel("Date");
    private JTextField dateBillText = new JTextField(10);
    private JLabel tableBill = new JLabel("Table");
    private JTextField tableBillText = new JTextField(4);
    private JButton generate = new JButton("Generate bill");
    private Restaurant restaurant;
    private JPanel panel2 = new JPanel();
    private JTable orders = new JTable();
    private Observer observer;
    private FileWriter fr;

    public JComboBox getSelectItem() {
        return selectItem;
    }

    public WaiterGraphicalUserInterface(Restaurant restaurant, ChefGraphicalUserInterface chefGraphicalUserInterface, FileWriter fr) {

        this.fr = fr;
        observer = new Observer(chefGraphicalUserInterface);
        this.restaurant = restaurant;
        JFrame frame = new JFrame("WaiterGraphicalUserInterface");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(3, 1));

        JPanel panel11 = new JPanel();
        panel11.add(orderIdCreate);
        panel11.add(orderIdCreateText);
        panel11.add(date);
        panel11.add(dateText);
        panel11.add(table);
        panel11.add(tableText);
        panel11.add(select);
        panel11.add(selectItem);
        addItems.addActionListener(new AddItem());
        panel11.add(addItems);
        panel11.add(selectedItemsText);
        create.addActionListener(new CreateOrder());
        panel11.add(create);

        panel1.add(panel11);

        JPanel panel13 = new JPanel();
        panel13.add(orderNrCompute);
        panel13.add(orderNrComputeText);
        panel13.add(dateCompute);
        panel13.add(dateComputeText);
        panel13.add(tableCompute);
        panel13.add(tableComputeText);
        compute.addActionListener(new ComputePrice());
        panel13.add(compute);
        panel13.add(price);

        panel1.add(panel13);

        JPanel panel14 = new JPanel();
        panel14.add(orderNrBill);
        panel14.add(orderNrBillText);
        panel14.add(dateBill);
        panel14.add(dateBillText);
        panel14.add(tableBill);
        panel14.add(tableBillText);
        generate.addActionListener(new GenerateBill());
        panel14.add(generate);

        panel1.add(panel14);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order nr.");
        model.addColumn("Date");
        model.addColumn("Table");
        model.addColumn("Items");
        orders = new JTable(model);
        orders.setRowHeight(100);
        panel2.add(new JScrollPane(orders));

        panel.add(panel1);
        panel.add(panel2);
        frame.add(panel);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    class AddItem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            selectedItemsText.setText(selectedItemsText.getText() + "\n" + selectItem.getSelectedItem());
        }
    }

    class CreateOrder implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<MenuItem> menuItems = new ArrayList<>();
            String[] names = selectedItemsText.getText().split("\n");
            String s = "<html>";
            int ok=0;//conditie pentru a verifica daca sunt produse composite in comanda
            for (int i = 1; i < names.length; i++) {
                s = s + names[i];
                s = s + "<br>";
                menuItems.add(restaurant.findItem(names[i]));
                if(restaurant.findItem(names[i]) instanceof CompositeProduct){
                    ok=1;//s-a gasit un produse composite
                }
            }
            s = s + "</html>";
            int id = Integer.parseInt(orderIdCreateText.getText());
            String date = dateText.getText();
            int table = Integer.parseInt(tableText.getText());
            restaurant.createOrder(id, date, table, menuItems);
            DefaultTableModel model = (DefaultTableModel) orders.getModel();
            String[] row = {String.valueOf(id), date, String.valueOf(table), s};
            model.addRow(row);
            orders = new JTable(model);
            if(ok==1){//daca comanda contine un composite, atunci bucatarul este anuntat
                observer.update(names);
            }
        }
    }

    class ComputePrice implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderNrComputeText.getText());
            String date = dateComputeText.getText();
            int table = Integer.parseInt(tableComputeText.getText());
            Order order = new Order(id, date, table);
            price.setText("Price: " + restaurant.computePrice(order));
        }
    }

    class GenerateBill implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(orderNrBillText.getText());
            String date = dateBillText.getText();
            int table = Integer.parseInt(tableBillText.getText());
            Order order = new Order(id, date, table);
            fr.createBillFile(order);
        }
    }

}
