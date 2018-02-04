package pl.woonkievitch.view;

import org.apache.log4j.Logger;
import pl.woonkievitch.Exception.EmptyOrderException;
import pl.woonkievitch.controller.MainController;
import pl.woonkievitch.event.changeRecipeValue.ChangeRecipeValueEvent;
import pl.woonkievitch.event.changeRecipeValue.ChangeRecipeValueListener;
import pl.woonkievitch.view.MultiLang.MultiLangButton;
import pl.woonkievitch.view.MultiLang.MultiLangLabel;
import pl.woonkievitch.utility.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.YELLOW;
import static java.awt.Color.black;
import static java.awt.Color.green;

public class MainScreen extends JPanel implements ChangeRecipeValueListener {
    private static final Logger logger = Logger.getLogger(MainScreen.class);
    public Menu menu;
    public JPanel container;
    private final MainController mainController;
    JLabel price;
    JLabel valueOfPrice;

    public MainScreen() {
        mainController = new MainController(this);
        menu = new Menu(this.mainController);
        setLayout(new BorderLayout());
        container = new JPanel();
        container.setBackground(green);
        container.setLayout(new BorderLayout());
        add(container, BorderLayout.CENTER);

        //Container for summary
        JPanel summary = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHEAST;
        constraints.fill = GridBagConstraints.NONE;

        price = null;
        price = new MultiLangLabel("price");
        valueOfPrice = new JLabel(" 0,00 zł");
        MultiLangButton orderButton = new MultiLangButton("makeOrder");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try{
                            Order.getInstance().realize();
                        }catch(EmptyOrderException e){
                            logger.error(e);
                        }
                        return null;
                    }
                };
                worker.execute();
            }
        });

        summary.add(price, constraints);
        summary.add(valueOfPrice, constraints);
        summary.add(orderButton, constraints);
        summary.setBackground(YELLOW);
        summary.setPreferredSize(new Dimension(300, 75));
        add(summary, BorderLayout.PAGE_END);

        Order.getInstance().addChangeValueListener(this);

        menu.setPreferredSize(new Dimension(300, 50));
        menu.setBackground(black);
        add(menu, BorderLayout.PAGE_START);
        menu.button1.doClick();
    }
    @Override
    public void valueChangeEvent(ChangeRecipeValueEvent e) {
        String currency = "zł";
        double value = Order.getInstance().getValue();
        String price = String.format(" %.2f %s", value, currency).replace(".", ",");
        valueOfPrice.setText(price);
        revalidate();
        repaint();
    }
}
