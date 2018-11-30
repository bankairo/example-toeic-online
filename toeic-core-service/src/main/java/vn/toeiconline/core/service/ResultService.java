package vn.toeiconline.core.service;

import vn.toeiconline.core.dto.ExaminationQuestionDTO;
import vn.toeiconline.core.dto.ResultDTO;

import java.util.List;

public interface ResultService {
    ResultDTO save(String userName, Integer examinationId, List<ExaminationQuestionDTO> examinationQuestionDTOS);
}
