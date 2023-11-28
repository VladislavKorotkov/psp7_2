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

    public List<Citizens> searchCitizensByLanguageAndCity(String language, String cityName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        List<Citizens> citizens = null;

        try {
            transaction = session.beginTransaction();

            String hql = "FROM Citizens c WHERE c.language = :language AND c.city.name = :cityName";
            Query query = session.createQuery(hql);
            query.setParameter("language", language);
            query.setParameter("cityName", cityName);

            citizens = query.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return citizens;
    }

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
