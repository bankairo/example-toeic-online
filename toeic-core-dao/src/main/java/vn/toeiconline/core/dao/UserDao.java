package vn.toeiconline.core.dao;

import vn.toeiconline.core.data.dao.GenericDao;
import vn.toeiconline.core.persistence.entity.UserEntity;

import java.util.List;

public interface UserDao extends GenericDao<Integer, UserEntity> {
    Object[] checkLogin(String name, String password) throws Exception;
    List<UserEntity> findByUser(List<String> names);
}
