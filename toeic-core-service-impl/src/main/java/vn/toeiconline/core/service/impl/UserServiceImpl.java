package vn.toeiconline.core.service.impl;

import org.apache.commons.lang.StringUtils;
import vn.toeiconline.core.dao.UserDao;
import vn.toeiconline.core.daoimpl.UserDaoImpl;
import vn.toeiconline.core.dto.CheckLogin;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.dto.UserImportDTO;
import vn.toeiconline.core.persistence.entity.RoleEntity;
import vn.toeiconline.core.persistence.entity.UserEntity;
import vn.toeiconline.core.service.UserService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.UserBeanUtil;

import java.sql.Timestamp;
import java.util.*;

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

    public void validateUserImport(List<UserImportDTO> userImportDTOS) {
        List<String> names = new ArrayList<String>();
        List<String> roles = new ArrayList<String>();

        for (UserImportDTO item: userImportDTOS) {
            names.add(item.getUserName());
            if (!roles.contains(item.getRoleName())) {
                roles.add(item.getRoleName());
            }
        }

        Map<String, UserEntity> userEntityMap = new HashMap<String, UserEntity>();
        Map<String, RoleEntity> roleEntityMap = new HashMap<String, RoleEntity>();

        if (names.size() > 0) {
            List<UserEntity> userEntities = SingletonDaoUtil.getUserDaoInstance().findByUser(names);
            for (UserEntity item: userEntities) {
                userEntityMap.put(item.getName().toUpperCase(), item);
            }
        }

        if (roles.size() > 0) {
            List<RoleEntity> roleEntities = SingletonDaoUtil.getRoleDaoInstance().findByRole(roles);
            for (RoleEntity item: roleEntities) {
                roleEntityMap.put(item.getName().toUpperCase(), item);
            }
        }

        for (UserImportDTO item: userImportDTOS) {
            String message = "";
            if (item.isValid()) {
                UserEntity userEntity = userEntityMap.get(item.getUserName().toUpperCase());
                if (userEntity != null) {
                    message += "<br/>";
                    message += "Tên đăng nhập đã tồn tại";
                }

                RoleEntity roleEntity = roleEntityMap.get(item.getRoleName().toUpperCase());
                if (roleEntity == null) {
                    message += "<br/>";
                    message += "Vai trò không tồn tại";
                }
                if (StringUtils.isNotBlank(message)) {
                    item.setValid(false);
                    item.setMessage(message.substring(5));
                }
        }
        }
    }

    public void saveUserImport(List<UserImportDTO> userImportDTOS) {
        for (UserImportDTO item: userImportDTOS) {
            if (item.isValid()) {
                UserEntity entity = new UserEntity();
                entity.setName(item.getUserName());
                entity.setPassword(item.getPassword());
                entity.setFullName(item.getFullName());
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                entity.setCreatedDate(timestamp);
                RoleEntity roleEntity = SingletonDaoUtil.getRoleDaoInstance().findEqualUnique("name", item.getRoleName());
                entity.setRoleEntity(roleEntity);
                SingletonDaoUtil.getUserDaoInstance().save(entity);
            }
        }
    }
}
