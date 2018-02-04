package pl.woonkievitch.view.order;

import org.apache.log4j.Logger;
import pl.woonkievitch.Exception.IncorrectStringPathException;
import pl.woonkievitch.controller.MealsOrderController;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.utility.ImageUtil;
import pl.woonkievitch.utility.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProductGrid extends JPanel {
    private final MealsOrderController controller;
    private final Logger logger = Logger.getLogger(this.getClass());
//    private JList jList;
    private final GridLayout gridLayout;
    private int itemCounts;
    private final int columns;
    private final Order instance;
    public ProductGrid(MealsOrderController controller) {
        instance = Order.getInstance();
        this.controller = controller;
        itemCounts = 0;
        columns = 3;
        gridLayout = new GridLayout(1, columns);
            setLayout(new FlowLayout());
        setBackground(Color.white);
        setPreferredSize(new Dimension(500, 700));
        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    public void addButton(Meal meal){
        itemCounts++;
        int buttonHeight = 150;
        ImageIcon image = null;
        try {
            image = ImageUtil.getInstance().getImage(meal.getImgUrl(), 80, 80);
        } catch (IncorrectStringPathException e) {
            e.printStackTrace();
        }
        JButton button = new MealButton(meal.getName(), meal.getPrice(), image);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setPreferredSize(new Dimension(150, buttonHeight));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        instance.addItem(meal);
                        return null;
                    }
                };
                worker.execute();
            }
        });
        int row = (int) Math.ceil((double) itemCounts/columns);
        System.out.println(row+"");
        gridLayout.setRows(row);
        int height = (int) Math.ceil(buttonHeight*row*1.2);
        setPreferredSize(new Dimension(400, height));
        add(button);
    }
}
