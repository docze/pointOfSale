package pl.woonkievitch.dao.impl;

import pl.woonkievitch.Exception.OrdersNotFoundException;
import pl.woonkievitch.dao.OrderDAO;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.domain.Orders;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusEvent;
import pl.woonkievitch.utility.Order;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;

import static pl.woonkievitch.SetupInt.PICK_UP;

public class OrderDAOImpl implements OrderDAO {
    EntityManagerFactory entityManagerFactory;

    public OrderDAOImpl() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("pointOfSaleDatabase");
    }

    @Override
    public List<Orders> findAll() {
        List<Orders> resultList;
        EntityManager em = entityManagerFactory.createEntityManager();

        TypedQuery<Meal> mealsQuery = em.createQuery("select m from Meal m", Meal.class);
        List<Meal> mealList = mealsQuery.getResultList();

        TypedQuery<Orders> ordersQuery = em.createQuery("select o from Orders o join  o.meals meal where meal in :meals", Orders.class);
        ordersQuery.setParameter("meals", mealList);
        resultList = ordersQuery.getResultList();

        Iterator<Orders> iterator = resultList.iterator();
        while (iterator.hasNext()){
            Orders next = iterator.next();
            List<Meal> meals = next.getMeals();
            System.out.printf("%d", next.getId());
            for(Meal meal:meals){
                System.out.printf("%s", meal.getName());
            }
            System.out.println("");
        }
        em.close();
        return resultList;
    }

    @Override
    public List<Orders> findByStatus(String status) throws OrdersNotFoundException {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Orders> query = em.createQuery("select o from Orders o where o.status = :status", Orders.class);
        query.setParameter("status", status);
        List<Orders> resultList = query.getResultList();
        if(resultList!=null){
            return resultList;
        }else{
            throw new OrdersNotFoundException();
        }

    }

    @Override
    public boolean addOrder(Orders orders) {
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            em.persist(orders);
            transaction.commit();
            em.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void remove(int id){
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Orders> query = em.createQuery("select o from Orders o where o.id = :id", Orders.class);
        query.setParameter("id", id);
        Orders singleResult = query.getSingleResult();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(singleResult);
        transaction.commit();
        em.close();
        Order.getInstance().fireChangeOrderList(new ChangeOrderStatusEvent(this));
    }

    @Override
    public void toPickUp(int id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<Orders> query = em.createQuery("select o from Orders o where o.id = :id", Orders.class);
        query.setParameter("id", id);
        Orders singleResult = query.getSingleResult();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        singleResult.setStatus(PICK_UP);
        transaction.commit();
        em.close();
        Order.getInstance().fireChangeOrderList(new ChangeOrderStatusEvent(this));
    }
}
