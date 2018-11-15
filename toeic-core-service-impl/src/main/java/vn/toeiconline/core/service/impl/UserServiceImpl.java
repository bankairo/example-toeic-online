package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dao.UserDao;
import vn.toeiconline.core.daoimpl.UserDaoImpl;
import vn.toeiconline.core.dto.CheckLogin;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.persistence.entity.UserEntity;
import vn.toeiconline.core.service.UserService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    public Object[] findUserByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        Object[] objects = SingletonDaoUtil.getUserDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit);
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for (UserEntity item: (List<UserEntity>)objects[1]) {
            UserDTO dto = UserBeanUtil.entity2Dto(item);
            userDTOS.add(dto);
        }
        objects[1] = userDTOS;
        return objects;
    }

    public UserDTO findUserById(Integer id) {
        UserEntity entity = SingletonDaoUtil.getUserDaoInstance().findById(id);
        UserDTO dto = UserBeanUtil.entity2Dto(entity);
        return dto;
    }

    public void saveUser(UserDTO dto) {
        UserEntity entity = UserBeanUtil.dto2Entity(dto);
        SingletonDaoUtil.getUserDaoInstance().save(entity);
    }

    public UserDTO updateUser(UserDTO dto) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(createdDate);
        UserEntity entity = UserBeanUtil.dto2Entity(dto);
        entity = SingletonDaoUtil.getUserDaoInstance().update(entity);
        dto = UserBeanUtil.entity2Dto(entity);
        dto.setCreatedDate(createdDate);
        return dto;
    }

    public CheckLogin checkLogin(String name, String password) {
        CheckLogin checkLogin = new CheckLogin();
        if (name != null && password != null) {
            Object[] objects = SingletonDaoUtil.getUserDaoInstance().checkLogin(name, password);
            checkLogin.setExist((Boolean) objects[0]);
            checkLogin.setRoleName((String) objects[1]);
        }
        return checkLogin;
    }
}
