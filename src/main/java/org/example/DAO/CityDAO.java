package org.example.DAO;

import org.example.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class CityDAO {
    private SessionFactory sessionFactory;

    public CityDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }


}
