package vn.toeiconline.core.service;

import vn.toeiconline.core.dto.CheckLogin;
import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.dto.UserImportDTO;

import java.util.List;
import java.util.Map;

public interface UserService {
    Object[] findUserByProperty(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit);
    UserDTO findUserById(Integer id);
    void saveUser(UserDTO dto);
    UserDTO updateUser(UserDTO dto);
    CheckLogin checkLogin(String name, String password) throws Exception;
    void validateUserImport(List<UserImportDTO> userImportDTOS);
    void saveUserImport(List<UserImportDTO> userImportDTOS);
}
