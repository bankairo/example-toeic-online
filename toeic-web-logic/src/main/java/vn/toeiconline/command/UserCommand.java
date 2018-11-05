package vn.toeiconline.command;

import vn.toeiconline.core.dto.UserDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

public class UserCommand extends AbstractCommand<UserDTO> {
    public UserCommand(){
        this.pojo = new UserDTO();
    }
}
