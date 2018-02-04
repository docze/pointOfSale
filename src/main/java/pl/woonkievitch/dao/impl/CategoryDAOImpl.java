package pl.woonkievitch.dao.impl;

import pl.woonkievitch.Exception.CategoryNotFoundException;
import pl.woonkievitch.dao.CategoryDAO;
import pl.woonkievitch.domain.Category;
import pl.woonkievitch.domain.Meal;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl extends DAOImpl implements CategoryDAO {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Category> findAll() throws CategoryNotFoundException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pointOfSaleDatabase");
        EntityManager entityManager = emf.createEntityManager();
        TypedQuery<Category> categoryQuery = entityManager.createQuery("select c from Category c", Category.class);
        List<Category> category = categoryQuery.getResultList();
        entityManager.close();
        if(category != null){
            return category;
        }else{
            throw new CategoryNotFoundException();
        }

    }

    @Override
    public void addCategory(Category category, List<Meal> meals){
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(category);
        try {
            for (Meal meal : meals) {
                entityManager.persist(meal);
            }
        }catch (NullPointerException e){
            System.out.println(e);
        }
        entityManager.getTransaction().commit();
        boolean contains = entityManager.contains(category);
        entityManager.close();
    }

    @Override
    public boolean deleteCategory(Category category) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.remove(category);
        entityManager.getTransaction().commit();
        boolean contains = !entityManager.contains(category);
        entityManager.close();
        return contains;
    }
}
