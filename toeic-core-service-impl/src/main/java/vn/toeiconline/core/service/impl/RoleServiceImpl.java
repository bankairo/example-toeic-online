package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dao.RoleDao;
import vn.toeiconline.core.daoimpl.RoleDaoImpl;
import vn.toeiconline.core.dto.RoleDTO;
import vn.toeiconline.core.persistence.entity.RoleEntity;
import vn.toeiconline.core.service.RoleService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.RoleBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    public List<RoleDTO> findAll() {
        List<RoleDTO> roleDTOS = new ArrayList<RoleDTO>();
        List<RoleEntity> roleEntities = SingletonDaoUtil.getRoleDaoInstance().findAll();
        for (RoleEntity item: roleEntities) {
            RoleDTO dto = RoleBeanUtil.entity2Dto(item);
            roleDTOS.add(dto);
        }
        return roleDTOS;
    }
}
