package vn.toeiconline.command;

import vn.toeiconline.core.dto.ExaminationDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

public class ExaminationCommand extends AbstractCommand<ExaminationDTO> {
    public ExaminationCommand() {
        this.pojo = new ExaminationDTO();
    }
}
