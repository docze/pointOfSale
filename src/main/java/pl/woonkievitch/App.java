package pl.woonkievitch;

import org.apache.log4j.Logger;
import pl.woonkievitch.dao.impl.OrderDAOImpl;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.utility.InitDatabase;
import pl.woonkievitch.utility.I18NFacade;
import pl.woonkievitch.utility.Localization.LocalizationClient;
import pl.woonkievitch.utility.Localization.LocalizationDTO;

import javax.swing.*;
import java.io.IOException;
import java.util.Properties;

class App
{
    private static final Logger logger = Logger.getLogger(App.class);
    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        I18NFacade.init();
       Properties properties = new Properties();
       try{
            properties.load(App.class.getResourceAsStream("/application.properties"));
            String first = properties.getProperty("first");
           if(first.equals("true")) {
               InitDatabase db = new InitDatabase();
               properties.setProperty("first", "false");
           }
       } catch (IOException e) {
           logger.error(e.getMessage());
       }
        logger.info("Application is started");
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.findAll();
        ScreenManager screenManager = new ScreenManager();


    }
    static  Meal createMeal(String name, double price, int kcal, String url){
        Meal meal = new Meal();
        meal.setName(name);
        meal.setPrice(price);
        meal.setImgUrl(url);
        meal.setKcal(kcal);
        return meal;
    }
}
