package vn.toeiconline.core.service;

import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.dto.ExerciseQuestionImportDTO;

import java.util.List;
import java.util.Map;

public interface ExerciseQuestionService {
    Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause);
    ExerciseQuestionDTO findExerciseQuestionById(Integer id);
    void save(ExerciseQuestionDTO dto);
    ExerciseQuestionDTO update(ExerciseQuestionDTO dto);
    Integer delete(List<Integer> ids);
    void validateImportExerciseQuestionImport(List<ExerciseQuestionImportDTO> importDTOS);
    void saveImportExerciseQuestionList(List<ExerciseQuestionImportDTO> importDTOS);
}
