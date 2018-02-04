package pl.woonkievitch.view.order;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Summary extends JPanel{

    private GridBagConstraints constraints;
    private JTextArea priceArea;
    private JButton makeOrder;
    private String CURRENCY;
    private String PRICE;

    public Summary() {
        PRICE = ResourceBundle.getBundle("lang", Locale.US).getString("price");
        CURRENCY = "zł";
        constraints = new GridBagConstraints();

        setPreferredSize(new Dimension(300, 100));
        setBackground(Color.orange);
        setLayout(new GridBagLayout());

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;
        constraints.weightx = 1;
        priceArea = new JTextArea(String.format("%s %.2f %s", PRICE, 0.00, CURRENCY));
        constraints.gridx = 2;

        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        add(priceArea, constraints);

        makeOrder = new JButton("Zamów");
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridx = 3;
        add(makeOrder, constraints);
    }
}
