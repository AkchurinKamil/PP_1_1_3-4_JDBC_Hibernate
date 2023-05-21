package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import static jm.task.core.jdbc.util.Util.getSessionFactory;


public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String sql = ("create table if not exists users" +
                    "(" +
                    "    name     varchar(45) null," +
                    "    lastName varchar(45) null," +
                    "    age      int          null," +
                    "    id       int auto_increment" +
                    "        primary key" +
                    ");");
           Query query = session.createSQLQuery(sql).addEntity(User.class);
           query.executeUpdate();
           transaction.commit();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            getSessionFactory().close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            getSessionFactory().close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = getSessionFactory().openSession().createQuery("FROM User").list();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }finally {
            getSessionFactory().close();
        }
    }
}
