package org.example.dao;

import org.example.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.example.util.Util;
import org.hibernate.SessionFactory;


import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    public static Session getSession() throws HibernateException {
        return sessionFactory.openSession();
    }

    @Override
    public void createUsersTable() {
        final Session session = getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    " ID INT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(15), last_name VARCHAR(15), age INT);").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        final Session session = getSession();
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users ").addEntity(User.class).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            try {
                session.getTransaction().rollback();
                throw e;
            } finally {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("User с именем" + " – " + name + " " + "добавлен в базу данных.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return getSession().createQuery("from User").list();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getSession()) {
            session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
