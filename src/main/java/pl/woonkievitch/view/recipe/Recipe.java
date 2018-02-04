package pl.woonkievitch.view.recipe;

import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.event.changeOrderList.ChangeOrderListEvent;
import pl.woonkievitch.event.changeOrderList.ChangeOrderListListener;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusEvent;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusListener;
import pl.woonkievitch.renderer.ButtonCellEditor;
import pl.woonkievitch.renderer.ButtonRender;
import pl.woonkievitch.utility.ChangeOrderListFacade;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.utility.Order;

import javax.swing.*;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static pl.woonkievitch.SetupInt.REMOVE_MEAL;

public class Recipe extends JPanel implements ChangeOrderListListener, ChangeOrderStatusListener {
    String[] columnNames;
    public Recipe() {
        List<Meal> meals = Order.getInstance().getMeals();
        setLayout(new BorderLayout());

        columnNames = new String[]{"Nazwa", "Cena", ""};
        Object[][] data;
        data = prepareDataTable(meals);
        Order.getInstance().addStatusListener(this);
        ChangeOrderListFacade.addListener(this);
        if(meals.size() > 0){
            createTable(data);
        }
    }

    private void createTable(Object[][] data){
        JTable table = new JTable(data, columnNames);
        String columnName = table.getColumnName(2);
        table.getColumnModel().getColumn(2).setCellEditor( new ButtonCellEditor(REMOVE_MEAL));
        table.getColumn(columnName).setCellRenderer(new ButtonRender(REMOVE_MEAL));
        setPreferredSize(new Dimension(600, 400));
        add(table);
        setBackground(Color.white);
    }
    private Object[][] prepareDataTable(List<Meal> meals){ ;
        Object[][] data = new Object[meals.size()][3];
        int i = 0;
        System.out.println(meals.size()+"ROZMIAR");
        for(Meal meal:meals){
            try {
                data[i][0] = I18NFacade.getMessage(meal.getName());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            data[i][1] = String.format("%.2f %s", meal.getPrice(), " zł");
            data[i][2] = meal.getName();
            i++;
        }
        return data;
    }



    @Override
    public void changeOrderListEvent(ChangeOrderListEvent e) {
        List<Meal> meals = Order.getInstance().getMeals();
        Object[][] data= prepareDataTable(meals);
        remove(0);
        if(meals.size()>0){
            createTable(data);
        }
        revalidate();
        repaint();
    }

    @Override
    public void orderStatusChange(ChangeOrderStatusEvent e) {
        changeOrderListEvent(new ChangeOrderListEvent(this));
    }
}
