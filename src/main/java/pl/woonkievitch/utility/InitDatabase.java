package pl.woonkievitch.utility;

import pl.woonkievitch.dao.impl.MealDAOImpl;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.domain.Orders;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitDatabase {
    EntityManager entityManager;
    EntityManagerFactory emf;
    public InitDatabase(){
        URL url = getClass().getClassLoader().getResource("img/categories/burgers.png");
        System.out.println(url.toString());
        emf = Persistence.createEntityManagerFactory("pointOfSaleDatabase");


        createBugers();
        createDrinks();


    }

    private void createBugers(){
        EntityManager em = emf.createEntityManager();
        Meal bigmac = createMeal("BigMac", 9.00, 500, "img/meals/burgers/bigMac.png");
        Meal cheeseburger = createMeal("Cheeseburger", 3.00, 250, "img/meals/burgers/cheeseBurger.png");
        Meal chikenburger = createMeal("Chickenburger", 5.00, 300, "img/meals/burgers/chickenburger.png");
        Meal doubleWhooper = createMeal("DoubleWhooper", 5.00, 300, "img/meals/burgers/doubleWhooper.png");
        Meal flamegrilledburger = createMeal("FlameGrilledBurger", 5.00, 300, "img/meals/burgers/flamegrilledburger.png");
        Meal mcroyal = createMeal("McRoyal", 5.00, 300, "img/meals/burgers/mcroyal.png");
        Meal overburger = createMeal("OverBurger", 8.20, 700, "img/meals/burgers/overburger.png");
        Meal pomodoreburger = createMeal("pomodoreBurger", 8.60, 700, "img/meals/burgers/pomodoreburger.png");

        List<Meal> meals = new ArrayList<>();
        meals.add(bigmac);
        meals.add(cheeseburger);
        meals.add(chikenburger);
        meals.add(doubleWhooper);
        meals.add(flamegrilledburger);
        meals.add(mcroyal);
        meals.add(overburger);
        meals.add(pomodoreburger);

        Category burgers = createCategory("Burgers", "img/categories/burgers.png", meals);
        em.getTransaction().begin();

        for (Iterator<Meal> it = meals.iterator(); it.hasNext();) {
            Meal enquiry = it.next();
            em.persist(enquiry);
            em.flush();
            em.clear();
        }
        em.persist(burgers);
        em.getTransaction().commit();
        em.close();
    }


    private void createDrinks(){
        EntityManager em = emf.createEntityManager();
        Meal cola = createMeal("cola", 9.00, 500, "img/meals/drinks/cola.png");
        Meal sprite = createMeal("sprite", 9.00, 500, "img/meals/drinks/sprite.png");
        Meal water = createMeal("water", 9.00, 500, "img/meals/drinks/water.png");
        Meal redbull = createMeal("redbull", 9.00, 500, "img/meals/drinks/redbull.png");
        Meal fanta = createMeal("fanta", 9.00, 500, "img/meals/drinks/fanta.png");
        Meal coffee = createMeal("coffee", 9.00, 500, "img/meals/drinks/coffee.png");

        List<Meal> meals = new ArrayList<>();
        meals.add(cola);
        meals.add(sprite);
        meals.add(water);
        meals.add(redbull);
        meals.add(fanta);
        meals.add(coffee);

        Category drinks = createCategory("Drinks", "img/categories/drinks.png", meals);
        em.getTransaction().begin();

        for (Iterator<Meal> it = meals.iterator(); it.hasNext();) {
            Meal enquiry = it.next();
            em.persist(enquiry);
            em.flush();
            em.clear();
        }
        em.persist(drinks);
        em.getTransaction().commit();
        em.close();
    }

    private Meal createMeal(String name, double price, int kcal, String url){
        Meal meal = new Meal();
        meal.setName(name);
        meal.setPrice(price);
        meal.setImgUrl(url);
        meal.setKcal(kcal);
        return meal;
    }

    private Category createCategory(String name, String url, List<Meal> meals){
        Category category = new Category();
        category.setName(name);
        category.setImgUrl(url);
        category.setMeals(meals);

        return category;
    }
}
