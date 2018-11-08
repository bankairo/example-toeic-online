package vn.toeiconline.core.test;

import org.junit.Test;
import vn.toeiconline.core.dao.RoleDao;
import vn.toeiconline.core.daoimpl.RoleDaoImpl;
import vn.toeiconline.core.persistence.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class RoleTest {
    @Test
    public void checkFindAll() {
        RoleDao roleDao = new RoleDaoImpl();
        List<RoleEntity> list = roleDao.findAll();
    }

    @Test
    public void checkUpdate() {
        RoleDao roleDao = new RoleDaoImpl();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(2);
        roleEntity.setName("USER_1");
        roleDao.update(roleEntity);
    }

    @Test
    public void checkSave() {
        RoleDao roleDao = new RoleDaoImpl();
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleId(4);
        roleEntity.setName("MANAGER");
        roleDao.save(roleEntity);
    }

    @Test
    public void checkFindById() {
        RoleDao roleDao = new RoleDaoImpl();
        RoleEntity roleEntity = roleDao.findById(1);
    }

    @Test
    public void checkFindByProperty() {
        RoleDao roleDao = new RoleDaoImpl();
        String property = null;
        Object value = null;
        String sortExpression = null;
        String sortDirection = null;
        sortExpression = "name";
        sortDirection = "2";
        Object[] listObj = roleDao.findByProperty(property, value, sortExpression, sortDirection, null, null);
    }

    @Test
    public void checkDelete() {
        RoleDao roleDao = new RoleDaoImpl();
        List<Integer> listId = new ArrayList<Integer>();
        listId.add(3);
        listId.add(4);
        Integer count = roleDao.delete(listId);
    }
}
