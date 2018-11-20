package vn.toeiconline.core.dao;

import vn.toeiconline.core.data.dao.GenericDao;
import vn.toeiconline.core.persistence.entity.RoleEntity;

import java.util.List;

public interface RoleDao extends GenericDao<Integer, RoleEntity> {
    List<RoleEntity> findByRole(List<String> roles);
}
