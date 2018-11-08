package vn.toeiconline.command;

import vn.toeiconline.core.dto.ListenGuidelineDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

public class ListenGuidelineCommand extends AbstractCommand<ListenGuidelineDTO> {
    public ListenGuidelineCommand() {
        this.pojo = new ListenGuidelineDTO();
    }
}
