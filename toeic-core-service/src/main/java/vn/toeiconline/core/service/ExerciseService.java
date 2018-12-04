package vn.toeiconline.core.service;

import vn.toeiconline.core.dto.ExerciseDTO;

import java.util.List;
import java.util.Map;

public interface ExerciseService {
    Object[] findExerciseByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause);
    List<ExerciseDTO> findAll();
}
