package vn.toeiconline.core.data.daoimpl;

import org.apache.log4j.Logger;
import org.hibernate.*;
import vn.toeiconline.core.common.constant.CoreConstant;
import vn.toeiconline.core.common.utils.HibernateUtil;
import vn.toeiconline.core.data.dao.GenericDao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AbstractDao<ID extends Serializable, T> implements GenericDao<ID, T> {
    private Class<T> persistenceClass;
    private final Logger log = Logger.getLogger(this.getClass());
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

    public Object[] findByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<T> list = new ArrayList<T>();
        Object totalRow = 0;
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            StringBuilder sql1 = new StringBuilder("from ");
            sql1.append(this.getPersistenceClassName()).append(" WHERE 1=1 ");
            String[] params = new String[property.size()];
            Object[] values = new Object[property.size()];
            if (property.size() > 0) {
                int i = 0;
                for (Map.Entry<String, Object> item: property.entrySet()) {
                    params[i] = item.getKey();
                    values[i] = item.getValue();
                    i++;
                }
                for (int i1 = 0; i1 < property.size(); i1++) {
                        sql1.append(" AND ").append("LOWER(" + params[i1] + ") LIKE '%' || :" + params[i1] + " || '%'");
                }
            }
            if (sortExpression != null && sortDirection != null) {
                sql1.append(" order by ").append(sortExpression).append(" " + (sortDirection.equals(CoreConstant.SORT_DESC)?"desc":"asc"));
            }
            Query query1 = session.createQuery(sql1.toString());
            if (property.size() > 0) {
                for (int i2 = 0; i2 < property.size(); i2++) {
                    query1.setParameter(params[i2], values[i2]);
                }
            }
            if (offset != null && offset >= 0) {
                query1.setFirstResult(offset);
            }
            if (limit != null && limit > 0) {
                query1.setMaxResults(limit);
            }
            list = query1.list();

            StringBuilder sql2 = new StringBuilder("select count(*) from ");
            sql2.append(this.getPersistenceClassName()).append(" WHERE 1=1 ");
            if (property.size() > 0) {
                for (int j = 0; j < property.size(); j++) {
                        sql2.append(" AND ").append("LOWER(" + params[j] + ") LIKE '%' || :" + params[j] + " || '%'");
                }
            }
            Query query2 = session.createQuery(sql2.toString());
            if (property.size() > 0) {
                for (int j1 = 0; j1 < property.size(); j1++) {
                    query2.setParameter(params[j1], values[j1]);
                }
            }
            totalRow = query2.list().get(0);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
        return count;
    }

    public T findEqualUnique(String property, Object value) {
        Session session = this.getSession();
        Transaction transaction = session.beginTransaction();
        T result = null;
        try {
            String sql = " FROM " + getPersistenceClassName() + " model WHERE model." + property + "= :value";
            Query query = session.createQuery(sql);
            query.setParameter("value", value);
            result = (T) query.uniqueResult();
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            log.error(e.getMessage(), e);
            throw e;
        } finally {
            session.close();
        }
        return result;
    }

}
