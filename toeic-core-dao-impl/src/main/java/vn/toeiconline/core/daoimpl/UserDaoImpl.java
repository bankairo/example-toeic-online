package vn.toeiconline.core.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.toeiconline.core.common.utils.HibernateUtil;
import vn.toeiconline.core.dao.UserDao;
import vn.toeiconline.core.data.daoimpl.AbstractDao;
import vn.toeiconline.core.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDao<Integer, UserEntity> implements UserDao {

    public Object[] checkLogin(String name, String password) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Boolean isExist = false;
        String roleName = null;
        try {
            StringBuilder sql = new StringBuilder("FROM UserEntity WHERE name= :name AND password= :password");
            Query query = session.createQuery(sql.toString());
            query.setParameter("name", name);
            query.setParameter("password", password);
            if (query.list().size() > 0) {
                isExist = true;
                roleName = ((UserEntity) query.uniqueResult()).getRole().getName();
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new Object[]{isExist, roleName};
    }

    public List<UserEntity> findByUser(List<String> names) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<UserEntity> result = new ArrayList<UserEntity>();
        try {
            String sql = "FROM UserEntity ue WHERE ue.name IN(:names) ";
            Query query = session.createQuery(sql);
            query.setParameterList("names", names);
            result = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }
}
