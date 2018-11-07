package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dao.UserDao;
import vn.toeiconline.core.daoimpl.UserDaoImpl;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.persistence.entity.UserEntity;
import vn.toeiconline.core.service.UserService;
import vn.toeiconline.core.utils.UserBeanUtil;

public class UserServiceImpl implements UserService {
    public UserDTO isUserExist(UserDTO dto) {
        UserDao userDao = new UserDaoImpl();
        UserEntity entity = userDao.findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
        return UserBeanUtil.entity2Dto(entity);
    }

    public UserDTO findRoleByUser(UserDTO dto) {
        UserDao userDao = new UserDaoImpl();
        UserEntity entity = userDao.findUserByUsernameAndPassword(dto.getName(), dto.getPassword());
        return UserBeanUtil.entity2Dto(entity);

    }
}
