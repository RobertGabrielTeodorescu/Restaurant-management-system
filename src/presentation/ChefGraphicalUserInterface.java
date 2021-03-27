package presentation;

import javax.swing.*;

public class ChefGraphicalUserInterface {

    private JTextArea text=new JTextArea(60,25);

    public JTextArea getText() {
        return text;
    }

    public ChefGraphicalUserInterface(){
        JFrame frame=new JFrame("ChefGraphicalUserInterface");
        JPanel panel = new JPanel();
        panel.add(text);
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}
