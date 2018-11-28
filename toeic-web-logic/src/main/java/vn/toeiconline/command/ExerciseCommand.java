package vn.toeiconline.command;

import vn.toeiconline.core.dto.ExerciseDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

public class ExerciseCommand extends AbstractCommand<ExerciseDTO> {
    public ExerciseCommand() {
        this.pojo = new ExerciseDTO();
    }
}
