package vn.toeiconline.core.dao;

import vn.toeiconline.core.data.dao.GenericDao;
import vn.toeiconline.core.persistence.entity.UserEntity;

public interface UserDao extends GenericDao<Integer, UserEntity> {
    Object[] checkLogin(String name, String password) throws Exception;
}
