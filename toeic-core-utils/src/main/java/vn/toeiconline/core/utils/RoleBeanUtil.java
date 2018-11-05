package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.RoleDTO;
import vn.toeiconline.core.persistence.entity.RoleEntity;

public class RoleBeanUtil {
    public RoleDTO entity2Dto(RoleEntity entity) {
        RoleDTO dto = new RoleDTO();
        dto.setRoleId(entity.getRoleId());
        dto.setName(entity.getName());
        return dto;
    }
    public RoleEntity dto2Entity(RoleDTO dto) {
        RoleEntity entity = new RoleEntity();
        entity.setRoleId(dto.getRoleId());
        entity.setName(dto.getName());
        return entity;
    }
}
