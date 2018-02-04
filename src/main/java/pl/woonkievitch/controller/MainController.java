package pl.woonkievitch.controller;

import pl.woonkievitch.ScreenManager;
import pl.woonkievitch.view.MainScreen;
import pl.woonkievitch.view.recipe.Recipe;
import pl.woonkievitch.view.order.MealOrder;

import javax.swing.*;
import java.awt.*;

public class MainController {
    ScreenManager screenManager;
    private MainScreen mainScreen;
    public MainController(MainScreen mainScreen){
        this.mainScreen = mainScreen;

    }

    public void showRecipe() {
        JPanel screen = new Recipe();
        setContainer(screen);
    }

    public void showOrder() {
        resetContainer();
        MealOrder screen = new MealOrder();
        final MealsOrderController controller = screen.getController();
        SwingWorker<JList, Void> worker = new SwingWorker<JList, Void>() {
            @Override
            protected JList doInBackground() throws Exception {
                System.out.println("swingworker");
                controller.setSidebar();
                return null;
            }
        };
        worker.execute();
        setContainer(screen);

    }

    private void setContainer(JPanel container) {
        resetContainer();
        mainScreen.container.add(container, BorderLayout.CENTER);
        mainScreen.revalidate();
        mainScreen.repaint();
    }
    private void resetContainer(){
        int size = mainScreen.container.getComponentCount();
        if(size > 0){
            for(int i = 0; i < size; i++){
                mainScreen.container.remove(i);
            }
        }
    }
}
