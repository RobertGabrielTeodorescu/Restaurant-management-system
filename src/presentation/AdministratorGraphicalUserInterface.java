package presentation;

import data.RestaurantSerializator;
import business.BaseProduct;
import business.CompositeProduct;
import business.MenuItem;
import business.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class AdministratorGraphicalUserInterface implements ItemListener {

    private JLabel nameBase = new JLabel("Name:");
    private JLabel priceBase = new JLabel("Price:");
    private JButton createBase = new JButton("Create base product");
    private JTextField nameBaseText = new JTextField(10);
    private JTextField priceText = new JTextField(10);
    private JLabel nameComposite = new JLabel("Name:");
    private JButton createComposite = new JButton("Create composite product");
    private JTextField nameCompositeText = new JTextField(10);
    private JLabel compositeAdd = new JLabel("Choose a composite product");
    private JComboBox compositeAddItems = new JComboBox();
    private JLabel listAdd = new JLabel("Item");
    private JComboBox listAddItems = new JComboBox();
    private JButton add = new JButton("Add item to composite");
    private JLabel compositeRemove = new JLabel("Choose a composite product");
    private JComboBox compositeRemoveItems = new JComboBox();
    private JLabel listRemove = new JLabel("Item");
    private JComboBox listRemoveItems = new JComboBox();
    private JButton remove = new JButton("Remove item from composite");
    private JLabel editItem = new JLabel("Name");
    private JComboBox editItemList = new JComboBox();
    private JLabel property = new JLabel("Property");
    private JComboBox propertyText = new JComboBox();
    private JLabel newValue = new JLabel("New value:");
    private JTextField newValueText = new JTextField(10);
    private JButton edit = new JButton("Edit Item");
    private JLabel deleteItem = new JLabel("Name: ");
    private JComboBox deleteItemList = new JComboBox();
    private JButton delete = new JButton("Delete Item");
    private JButton save = new JButton("Save");
    JPanel panel2 = new JPanel();

    Restaurant restaurant;
    JComboBox box;
    RestaurantSerializator restaurantSerializator;


    public AdministratorGraphicalUserInterface(Restaurant restaurant, JComboBox box, RestaurantSerializator restaurantSerializator) {

        this.restaurantSerializator = restaurantSerializator;
        this.restaurant = restaurant;
        this.box = box;

        selectCompositeItems();
        itemsList();
        MenuItem menuItem = restaurant.findItem((String) compositeRemoveItems.getSelectedItem());
        listRemoveItems.removeAllItems();
        for (MenuItem m : ((CompositeProduct) menuItem).getItems()) {
            listRemoveItems.addItem(m.getNume());
        }
        compositeRemoveItems.addItemListener(this);
        propertyText.addItem("name");
        propertyText.addItem("price");
        JFrame frame = new JFrame("AdministratorGraphicalUserInterface");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));

        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.yellow);
        panel1.setLayout(new GridLayout(6, 1));


        JPanel panel12 = new JPanel();
        //panel12.setLayout(new GridLayout(1,5));
        panel12.add(nameBase);
        panel12.add(nameBaseText);
        panel12.add(priceBase);
        panel12.add(priceText);
        createBase.addActionListener(new CreateBaseProductListener());
        panel12.add(createBase);

        JPanel panel13 = new JPanel();
        //panel13.setLayout(new GridLayout(1,3));
        panel13.add(nameComposite);
        panel13.add(nameCompositeText);
        createComposite.addActionListener(new CreateCompositeProductListener());
        panel13.add(createComposite);

        JPanel panel14 = new JPanel();
        //panel14.setLayout(new GridLayout(1,6));
        panel14.add(compositeAdd);
        panel14.add(compositeAddItems);
        panel14.add(listAdd);
        panel14.add(listAddItems);
        add.addActionListener(new AddListener());
        panel14.add(add);

        JPanel panel11 = new JPanel();
        panel11.add(compositeRemove);

        panel11.add(compositeRemoveItems);
        panel11.add(listRemove);
        panel11.add(listRemoveItems);
        remove.addActionListener(new RemoveListener());
        panel11.add(remove);

        JPanel panel15 = new JPanel();
        //panel15.setLayout(new GridLayout(1,7));
        panel15.add(editItem);
        panel15.add(editItemList);
        panel15.add(property);
        panel15.add(propertyText);
        panel15.add(newValue);
        panel15.add(newValueText);
        edit.addActionListener(new EditListener());
        panel15.add(edit);

        JPanel panel16 = new JPanel();
        //panel16.setLayout(new GridLayout(1,3));
        panel16.add(deleteItem);
        panel16.add(deleteItemList);
        delete.addActionListener(new DeleteListener());
        panel16.add(delete);

        panel1.add(panel12);
        panel1.add(panel13);
        panel1.add(panel14);
        panel1.add(panel11);
        panel1.add(panel15);
        panel1.add(panel16);

        save.addActionListener(new SaveButtonListener());
        panel16.add(save);

        actualizareTabel();
        for (MenuItem m : restaurant.getMenuItems()) {
            box.addItem(m.getNume());
        }
        panel.add(panel1);
        panel.add(panel2);
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    private void selectCompositeItems() {
        compositeAddItems.removeAllItems();
        compositeRemoveItems.removeAllItems();
        for (MenuItem menuItem : restaurant.getMenuItems()) {
            if (menuItem instanceof CompositeProduct) {
                compositeAddItems.addItem(menuItem.getNume());
                compositeRemoveItems.addItem(menuItem.getNume());
            }
        }
    }

    private void itemsList() {
        listAddItems.removeAllItems();
        deleteItemList.removeAllItems();
        editItemList.removeAllItems();
        for (MenuItem menuItem : restaurant.getMenuItems()) {
            listAddItems.addItem(menuItem.getNume());
            deleteItemList.addItem(menuItem.getNume());
            editItemList.addItem(menuItem.getNume());
        }
    }


    private void actualizareTabel() {
        panel2.removeAll();
        panel2.updateUI();
        String[] columnNames = {"Name", "Type", "Component items", "Price"};
        Object[][] data = new Object[restaurant.getMenuItems().size()][4];
        for (int i = 0; i < restaurant.getMenuItems().size(); i++) {
            data[i][0] = restaurant.getMenuItems().get(i).getNume();
            if (restaurant.getMenuItems().get(i) instanceof BaseProduct) {
                data[i][1] = "base";
                data[i][2] = "-";
            } else {
                data[i][1] = "composite";
                data[i][2] = restaurant.getMenuItems().get(i).toString();
            }
            data[i][3] = restaurant.getMenuItems().get(i).getPrice();
        }
        JTable table = new JTable(data, columnNames);
        table.setRowHeight(100);
        panel2.add(new JScrollPane(table));
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            MenuItem menuItem = restaurant.findItem((String) compositeRemoveItems.getSelectedItem());
            listRemoveItems.removeAllItems();
            for (MenuItem m : ((CompositeProduct) menuItem).getItems()) {
                listRemoveItems.addItem(m.getNume());
            }
        }
    }


    class CreateBaseProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameBaseText.getText();
            box.addItem(name);
            String price = priceText.getText();
            MenuItem menuItem = new BaseProduct(name, Integer.parseInt(price));
            restaurant.createNewItem(menuItem);
            itemsList();
            actualizareTabel();
        }
    }

    class CreateCompositeProductListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameCompositeText.getText();
            MenuItem menuItem = new CompositeProduct(name);
            restaurant.createNewItem(menuItem);
            compositeAddItems.addItem(menuItem.getNume());
            itemsList();
            selectCompositeItems();
            actualizareTabel();
        }
    }

    class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CompositeProduct composite = (CompositeProduct) restaurant.findItem((String) compositeAddItems.getSelectedItem());
            MenuItem menuItem = restaurant.findItem((String) listAddItems.getSelectedItem());
            restaurant.addItemtoComposite(composite, menuItem);
            compositeAddItems.setSelectedItem("Choose a composite product");
            actualizareTabel();
            menuItem = restaurant.findItem((String) compositeRemoveItems.getSelectedItem());
            listRemoveItems.removeAllItems();
            for (MenuItem m : ((CompositeProduct) menuItem).getItems()) {
                listRemoveItems.addItem(m.getNume());
            }
        }
    }

    class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CompositeProduct composite = (CompositeProduct) restaurant.findItem((String) compositeRemoveItems.getSelectedItem());
            MenuItem menuItem = restaurant.findItem((String) listRemoveItems.getSelectedItem());
            restaurant.removeItemFromComposite(composite, menuItem);
            compositeAddItems.setSelectedItem("Choose a composite product");
            listRemoveItems.removeAllItems();
            for (MenuItem m : composite.getItems()) {
                listRemoveItems.addItem(m.getNume());
            }
            actualizareTabel();
        }
    }

    class EditListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItem menuItem = restaurant.findItem((String) editItemList.getSelectedItem());
            restaurant.editMenuItem(menuItem, (String) propertyText.getSelectedItem(), newValueText.getText());
            itemsList();
            selectCompositeItems();
            actualizareTabel();
        }
    }

    class DeleteListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MenuItem menuItem = restaurant.findItem((String) deleteItemList.getSelectedItem());
            restaurant.deleteMenuItem(menuItem);
            box.removeAllItems();
            for (MenuItem m : restaurant.getMenuItems()) {
                box.addItem(m.getNume());
            }
            itemsList();
            selectCompositeItems();
            actualizareTabel();
            try {
                menuItem = restaurant.findItem((String) compositeRemoveItems.getSelectedItem());
                listRemoveItems.removeAllItems();
                for (MenuItem m : ((CompositeProduct) menuItem).getItems()) {
                    listRemoveItems.addItem(m.getNume());
                }
            } catch (Exception e1) {
                listRemoveItems.removeAllItems();
            }
        }
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            restaurantSerializator.save();
        }
    }

}
