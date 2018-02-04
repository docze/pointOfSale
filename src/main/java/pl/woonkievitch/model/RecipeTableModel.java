package pl.woonkievitch.model;

import com.sun.imageio.plugins.common.I18N;
import com.sun.javafx.binding.StringFormatter;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.view.MultiLang.MultiLangButton;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RecipeTableModel  extends AbstractTableModel {
    private List<Meal> meals;
    private String[] headerList = {"name", "price", "action"};

    public RecipeTableModel(List<Meal> meals) {
        super();
        this.meals = meals;
    }

    @Override
    public int getRowCount() {
        return meals.size();
    }

    @Override
    public int getColumnCount() {
        return headerList.length;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex == headerList.length){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Meal meal = meals.get(rowIndex);
        switch(columnIndex){
            case 0:
                try {
                    return I18NFacade.getMessage(meal.getName());
                } catch (UnsupportedEncodingException e) {
                    //e.printStackTrace();
                }
            case 1:
                return String.format("%.2f %s",meal.getPrice(), " zł").replace(".", ",");
            case 2:
                return meals;
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return headerList[column];
    }
}
