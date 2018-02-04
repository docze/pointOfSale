package pl.woonkievitch.utility;

import pl.woonkievitch.Exception.EmptyOrderException;
import pl.woonkievitch.dao.impl.OrderDAOImpl;
import pl.woonkievitch.domain.Meal;
import pl.woonkievitch.domain.Orders;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusEvent;
import pl.woonkievitch.event.changeOrderStatusEvent.ChangeOrderStatusListener;
import pl.woonkievitch.event.changeRecipeValue.ChangeRecipeValueEvent;
import pl.woonkievitch.event.changeRecipeValue.ChangeRecipeValueListener;

import javax.swing.event.EventListenerList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private EventListenerList recipeListenerList;
    private EventListenerList ordersListenerList;
    private List<Meal> meals;
    private double value;
    private static Order ourInstance = new Order();

    public static Order getInstance() {
        return ourInstance;
    }

    private Order() {
        value = 0;
        meals = new LinkedList<>();
        recipeListenerList = new EventListenerList();
        ordersListenerList = new EventListenerList();
    }

    public void addItem(Meal meal){
        double price = meal.getPrice();
        value += price;
        meals.add(meal);
        fireChangeRecipeValue(new ChangeRecipeValueEvent(this));
    }

    public boolean remove(String name){
        Iterator<Meal> iterator = meals.iterator();
        Meal searched = null;
        while(iterator.hasNext() && searched == null ){
            Meal next = iterator.next();
            if(next.getName().equals(name)) searched = next;
        }
        if(searched!=null){
            meals.remove(searched);
            value -= searched.getPrice();
            fireChangeRecipeValue(new ChangeRecipeValueEvent(this));
            return true;
        }
        return false;
    }

    public void realize() throws EmptyOrderException{
        if(meals.size() > 0){
        Orders order = new Orders();
        order.setValue(value);
        order.setStatus("realizing");
        order.setMeals(meals);
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orderDAO.addOrder(order);
        value = 0;
        meals = new LinkedList<>();
        fireChangeRecipeValue(new ChangeRecipeValueEvent(this));
        fireChangeOrderList(new ChangeOrderStatusEvent(this));
        }else{
            throw new EmptyOrderException();
        }
    }

    public void addChangeValueListener(ChangeRecipeValueListener listener) {
        recipeListenerList.add(ChangeRecipeValueListener.class, listener);
    }
    public void removeMyEventListener(ChangeRecipeValueListener listener) {
        recipeListenerList.remove(ChangeRecipeValueListener.class, listener);
    }

    public void addStatusListener(ChangeOrderStatusListener listener) {
        ordersListenerList.add(ChangeOrderStatusListener.class, listener);
    }
    public void removeStatuListener(ChangeOrderStatusListener listener) {
        ordersListenerList.remove(ChangeOrderStatusListener.class, listener);
    }

    public void fireChangeRecipeValue(ChangeRecipeValueEvent evt) {
        Object[] listeners = recipeListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ChangeRecipeValueListener.class) {
                ((ChangeRecipeValueListener) listeners[i + 1]).valueChangeEvent(evt);
            }
        }
    }
    public void fireChangeOrderList(ChangeOrderStatusEvent evt) {
        Object[] listeners = ordersListenerList.getListenerList();
        for (int i = 0; i < listeners.length; i = i + 2) {
            if (listeners[i] == ChangeOrderStatusListener.class) {
                ((ChangeOrderStatusListener) listeners[i + 1]).orderStatusChange(evt);
            }
        }
    }


    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Order{" + "meals=" + meals + ", value=" + value + '}';
    }

    public double getValue() {
        return value;
    }
}
