package vn.toeiconline.core.daoimpl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.toeiconline.core.common.utils.HibernateUtil;
import vn.toeiconline.core.dao.RoleDao;
import vn.toeiconline.core.data.daoimpl.AbstractDao;
import vn.toeiconline.core.persistence.entity.RoleEntity;
import vn.toeiconline.core.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class RoleDaoImpl extends AbstractDao<Integer, RoleEntity> implements RoleDao {

    public List<RoleEntity> findByRole(List<String> roleNames) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<RoleEntity> result = new ArrayList<RoleEntity>();
        try {
            String sql = "FROM RoleEntity re WHERE re.name IN(:roleNames)";
            Query query = session.createQuery(sql);
            query.setParameterList("roleNames", roleNames);
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
