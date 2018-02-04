package pl.woonkievitch.dao.impl;

import pl.woonkievitch.Exception.MealsNotFoundException;
import pl.woonkievitch.dao.MealDAO;
import pl.woonkievitch.domain.Meal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class MealDAOImpl extends DAOImpl implements MealDAO {

    @PersistenceContext
    EntityManager entityManager;



    @Override
    public List<Meal> findAll() throws MealsNotFoundException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Meal> meals = new ArrayList<Meal>();
        TypedQuery<Meal> mealsQuery = entityManager.createQuery("select m from Meal m", Meal.class);
        meals = mealsQuery.getResultList();
        entityManager.close();
        if(meals !=null){
            return meals;
        }else{
            throw new MealsNotFoundException();
        }
    }

    @Override
    public Meal findById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Meal> query = entityManager.createQuery("select m from Meal m where m.id = :id", Meal.class);
        query.setParameter("id", id);
        Meal result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    @Override
    public Meal findByName(String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pointOfSaleDatabase");
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Meal> query = entityManager.createQuery("select m from Meal m where m.name = :name", Meal.class);
        query.setParameter("name", name);
        Meal meals = query.getSingleResult();
        entityManager.close();
        return meals;
    }

    @Override
    public boolean addMeal(Meal meal) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(meal);
        entityManager.getTransaction().commit();
        boolean contains = entityManager.contains(meal);
        entityManager.close();

        return contains;
    }

    @Override
    public boolean deleteMeal(Meal meal) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.remove(meal);
        entityManager.getTransaction().commit();
        boolean contains = entityManager.contains(meal);
        entityManager.close();

        return !contains;
    }

}
