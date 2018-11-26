package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.ExerciseTypeDTO;
import vn.toeiconline.core.persistence.entity.ExerciseTypeEntity;

public class ExerciseTypeBeanUtil {
    public static ExerciseTypeDTO entity2Dto(ExerciseTypeEntity entity) {
        ExerciseTypeDTO dto = new ExerciseTypeDTO();
        dto.setExerciseTypeId(entity.getExerciseTypeId());
        dto.setName(entity.getName());
        dto.setCode(entity.getCode());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExerciseTypeEntity dto2Entity(ExerciseTypeDTO dto) {
        ExerciseTypeEntity entity = new ExerciseTypeEntity();
        entity.setExerciseTypeId(dto.getExerciseTypeId());
        entity.setName(dto.getName());
        entity.setCode(dto.getCode());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
