package pl.woonkievitch.view;

import org.apache.log4j.Logger;
import pl.woonkievitch.view.MultiLang.MultiLangButton;
import pl.woonkievitch.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private final MainController controller;
    private final Logger logger = Logger.getLogger(Menu.class);

    public JButton button1;
    public JButton button2;
    public JButton button3;


    public Menu(MainController mainController) {
        this.controller = mainController;
        GridLayout gridLayout = new GridLayout(1, 3);
        setLayout(gridLayout);
        addButtons();
    }

    private void addButtons() {
        button1 = new MultiLangButton("order");
        button2 = new MultiLangButton("recipe");


        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showOrder();
            }

        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.showRecipe();
            }
        });

        add(button1);
        add(button2);
    }

}
