package org.example.DAO;

import org.example.model.Citizens;
import org.example.model.City;
import org.example.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CitizensDAO {
    private SessionFactory sessionFactory;

    public CitizensDAO() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Citizens> getAll(){
        try (Session session = sessionFactory.openSession()) {
            Query<Citizens> query = session.createQuery("from Citizens", Citizens.class);
            return query.list();
        }
    }

//    public City getByName(String name){
//        try (Session session = sessionFactory.openSession()) {
//            Query<Dormitory> query = session.createQuery("from Dormitory where name= :name", Dormitory.class);
//            query.setParameter("name", name);
//            return query.getResultList().get(0);
//        }
//    }

    public Citizens create(Citizens citizens){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(citizens);
            tx.commit();
            return citizens;
        }
    }
    public Citizens update(Citizens citizens){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.update(citizens);
            tx.commit();
            return citizens;
        }
    }
    public void delete(Citizens citizens){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(citizens);
            tx.commit();
        }
    }
}
