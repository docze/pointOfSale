package pl.woonkievitch.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAOImpl {
    EntityManagerFactory entityManagerFactory;

    public DAOImpl(){
        //entityManagerFactory = Persistence.g("pointOfSaleDatabase");

    }
}
