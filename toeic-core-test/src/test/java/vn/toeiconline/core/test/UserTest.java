package vn.toeiconline.core.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import vn.toeiconline.core.dao.UserDao;
import vn.toeiconline.core.daoimpl.UserDaoImpl;
import vn.toeiconline.core.persistence.entity.UserEntity;

public class UserTest {
    private final Logger log = Logger.getLogger(this.getClass());

//    @Test
//    public void checkIsUserExist() {
//        UserDao userDao = new UserDaoImpl();
//        String name = "traan";
//        String password = "123456";
//        UserEntity entity = userDao.findUserByUsernameAndPassword(name, password);
//        if (entity != null) {
//            log.error("login success");
//        } else {
//            log.error("login fail");
//        }
//    }
//
//    @Test
//    public void checkFindRoleByUser() {
//        UserDao userDao = new UserDaoImpl();
//        String name = "traan";
//        String password = "123456";
//        UserEntity entity = userDao.findUserByUsernameAndPassword(name, password);
//        log.error(entity.getRole().getRoleId() + " - " + entity.getRole().getName());
//    }
}
