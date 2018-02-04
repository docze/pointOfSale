package pl.woonkievitch.view.order;

import pl.woonkievitch.controller.MealsOrderController;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.renderer.CategoryListRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import static java.awt.Color.*;

public class MealOrder extends JPanel{
    MealsOrderController mealsOrderController;
    public JPanel sidebar;
    public JPanel container;
    public JPanel productGridContainer;
    public JScrollPane productScrollPane;
    public JScrollPane categoryScrollPane;
    public JList categoryList;
    public JList productList;
    JLabel price;
    JLabel valueOfPrice;
    public MealOrder() {
        this.mealsOrderController = new MealsOrderController(this);
        setLayout(new BorderLayout());
        //Container for sidebar
        categoryList = new JList(new DefaultListModel<Category>());
        prepareCategoryList();
        categoryScrollPane = new JScrollPane(categoryList);
        sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(150,0));
        add(sidebar, BorderLayout.LINE_START);
        sidebar.add(categoryScrollPane);

        // Container for ProductGrid
        productGridContainer = new JPanel();//new ProductGrid();
        productGridContainer.setLayout(new BorderLayout());
        productGridContainer.setBackground(white);
        productList = new JList(new DefaultListModel<Meal>());
       // productScrollPane = new JScrollPane();
       // productGridContainer.add(productScrollPane);
        add(productGridContainer, BorderLayout.CENTER);


    }

    public MealsOrderController getController() {
        return mealsOrderController;
    }


    private void prepareCategoryList(){
        categoryList.setCellRenderer(new CategoryListRenderer());
        categoryList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting()){
                    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {

                            mealsOrderController.prepareMealList(categoryList.getSelectedValue());
                            return null;
                        }
                        @Override
                        protected void done() {
                            super.done();
                            revalidate();
                            repaint();
                        }
                    };
                    worker.execute();
                }
            }
        });
    }

}
