package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.ExerciseDTO;
import vn.toeiconline.core.persistence.entity.ExerciseEntity;

public class ExerciseBeanUtil {
    public static ExerciseDTO entity2Dto(ExerciseEntity entity) {
        ExerciseDTO dto = new ExerciseDTO();
        dto.setExerciseId(entity.getExerciseId());
        dto.setName(entity.getName());
        dto.setExerciseTypeDTO(new ExerciseTypeBeanUtil().entity2Dto(entity.getExerciseTypeEntity()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExerciseEntity dto2Entity(ExerciseDTO dto) {
        ExerciseEntity entity = new ExerciseEntity();
        entity.setExerciseId(dto.getExerciseId());
        entity.setName(dto.getName());
        entity.setExerciseTypeEntity(new ExerciseTypeBeanUtil().dto2Entity(dto.getExerciseTypeDTO()));
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
