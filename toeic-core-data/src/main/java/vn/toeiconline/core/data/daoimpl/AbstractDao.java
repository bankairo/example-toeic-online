package vn.toeiconline.core.data.daoimpl;

import org.hibernate.*;
import vn.toeiconline.core.common.constant.CoreConstant;
import vn.toeiconline.core.common.utils.HibernateUtil;
import vn.toeiconline.core.data.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class AbstractDao<ID extends Serializable, T> implements GenericDao<ID, T> {
    private Class<T> persistenceClass;
    public AbstractDao() {
        this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
    protected Session getSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
    private String getPersistenceClassName() {
        return this.persistenceClass.getSimpleName();
    }
    public List<T> findAll() {
        List<T> list = new ArrayList<T>();
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();

        try {
            StringBuilder hql = new StringBuilder("from ");
            hql.append(this.getPersistenceClassName());
            Query query = this.getSession().createQuery(hql.toString());
            list = query.list();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return list;
    }

    public T update(T entity) {
        T result = null;
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Object object = session.merge(entity);
            result = (T) object;
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return result;
    }

    public void save(T entity) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public T findById(ID id) {
        T result = null;
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            result = (T) session.get(this.persistenceClass, id);
            if (result == null) {
                throw new ObjectNotFoundException("NOT FOUND" + id, null);
            }
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public Object[] findByProperty(String property, Object value, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<T> list = new ArrayList<T>();
        Object totalRow = 0;
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder sql1 = new StringBuilder("from ");
            sql1.append(this.getPersistenceClassName());
            if (property != null && value != null) {
                sql1.append(" where ").append(property).append("= :value");
            }
            if (sortExpression != null && sortDirection != null) {
                sql1.append(" order by ").append(sortDirection).append(" " + (sortDirection.equals(CoreConstant.SORT_DESC)?"desc":"asc"));
            }
            Query query1 = session.createQuery(sql1.toString());
            if (value != null) {
                query1.setParameter("value", value);
            }
            if (offset != null && offset >= 0) {
                query1.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query1.setMaxResults(limit);
            }
            list = query1.list();

            StringBuilder sql2 = new StringBuilder("select count(*) from ");
            sql2.append(this.getPersistenceClassName());
            if (property != null && value != null) {
                sql2.append(" where ").append(property).append("= :value");
            }
            Query query2 = session.createQuery(sql2.toString());
            if (value != null) {
                query2.setParameter("value", value);
            }
            totalRow = query2.list().get(0);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return new Object[]{totalRow, list};
    }

    public Integer delete(List<ID> ids) {
        Integer count = 0;
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            for (ID item: ids) {
                T t = (T) session.get(this.persistenceClass, item);
                session.delete(t);
                count++;
            }
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
        return count;
    }

}
