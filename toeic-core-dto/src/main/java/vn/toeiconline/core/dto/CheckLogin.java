package vn.toeiconline.core.dto;

public class CheckLogin {
    private Boolean isExist;
    private String roleName;

    public Boolean getExist() {
        return isExist;
    }

    public void setExist(Boolean exist) {
        isExist = exist;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
