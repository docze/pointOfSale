package pl.woonkievitch.dao;

import pl.woonkievitch.Exception.OrdersNotFoundException;
import pl.woonkievitch.domain.Orders;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderDAO {
    @Transactional
    List<Orders> findAll();
    @Transactional
    List<Orders> findByStatus(String status) throws OrdersNotFoundException;
    boolean addOrder(Orders orders);
    void remove(int id);
    void toPickUp(int id);
}
