package pl.woonkievitch.controller;

import org.apache.log4j.Logger;
import pl.woonkievitch.Exception.CategoryNotFoundException;
import pl.woonkievitch.dao.CategoryDAO;
import pl.woonkievitch.dao.impl.CategoryDAOImpl;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.view.order.MealOrder;
import pl.woonkievitch.view.order.ProductGrid;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MealsOrderController {
    private static final Logger logger = Logger.getLogger(MealsOrderController.class);
    private MealOrder mealOrder;
    private DefaultListModel<Category> categories;

    public MealsOrderController(MealOrder mealOrder) {
        this.mealOrder = mealOrder;
    }

    public void setSidebar(){
        categories = (DefaultListModel<Category>) mealOrder.categoryList.getModel();
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        categories.clear();
        try {
            for(Category category: categoryDAO.findAll()) {
                categories.addElement(category);
            }
        } catch (CategoryNotFoundException e) {
            logger.error(e);
        }
    }


    public void prepareMealList(Object object){
        ProductGrid productGrid = new ProductGrid(this);
        Category category = (Category) object;
        List<Meal> meals = category.getMeals();
        int componentCount = mealOrder.productGridContainer.getComponentCount();

        for(int i = 0; i <componentCount; i++){
            mealOrder.productGridContainer.remove(i);
        }

        for (Meal meal : meals) {
            productGrid.addButton(meal);
        }
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(productGrid);
        mealOrder.productGridContainer.add(scrollPane);

    }

}
