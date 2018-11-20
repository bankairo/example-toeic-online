package vn.toeiconline.command;

import vn.toeiconline.core.dto.RoleDTO;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.dto.UserImportDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

import java.util.List;

public class UserCommand extends AbstractCommand<UserDTO> {
    public UserCommand(){
        this.pojo = new UserDTO();
    }
    private List<RoleDTO> roles;
    private Integer roleId;
    private List<UserImportDTO> userImportDTOS;

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<UserImportDTO> getUserImportDTOS() {
        return userImportDTOS;
    }

    public void setUserImportDTOS(List<UserImportDTO> userImportDTOS) {
        this.userImportDTOS = userImportDTOS;
    }

}
