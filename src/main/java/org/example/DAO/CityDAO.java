package org.example.DAO;

import org.example.model.City;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CityDAO {
    private SessionFactory sessionFactory;

    public CityDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<City> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<City> query = session.createQuery("from City", City.class);
            return query.list();
        }
    }

    public City getByName(String name){
        try (Session session = sessionFactory.openSession()) {
            Query<City> query = session.createQuery("from City where name= :name", City.class);
            query.setParameter("name", name);
            return query.getResultList().get(0);
        }
    }

    public City create(City city){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(city);
            tx.commit();
            return city;
        }
    }
    public City update(City city){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(city);
            tx.commit();
            return city;
        }
    }
    public void delete(City city){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(city);
            tx.commit();
        }
    }

}
