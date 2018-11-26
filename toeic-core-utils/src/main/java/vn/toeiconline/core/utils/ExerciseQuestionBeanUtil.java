package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.persistence.entity.ExerciseQuestionEntity;

public class ExerciseQuestionBeanUtil {
    public static ExerciseQuestionDTO entity2Dto(ExerciseQuestionEntity entity) {
        ExerciseQuestionDTO dto = new ExerciseQuestionDTO();
        dto.setExerciseQuestionId(entity.getExerciseQuestionId());
        dto.setImage(entity.getImage());
        dto.setAudio(entity.getAudio());
        dto.setQuestion(entity.getQuestion());
        dto.setOption1(entity.getOption1());
        dto.setOption2(entity.getOption2());
        dto.setOption3(entity.getOption3());
        dto.setOption4(entity.getOption4());
        dto.setCorrectAnswer(entity.getCorrectAnswer());
        dto.setExerciseDTO(new ExerciseBeanUtil().entity2Dto(entity.getExerciseEntity()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExerciseQuestionEntity dto2Entity(ExerciseQuestionDTO dto) {
        ExerciseQuestionEntity entity = new ExerciseQuestionEntity();
        entity.setExerciseQuestionId(dto.getExerciseQuestionId());
        entity.setImage(dto.getImage());
        entity.setAudio(dto.getAudio());
        entity.setQuestion(dto.getQuestion());
        entity.setOption1(dto.getOption1());
        entity.setOption2(dto.getOption2());
        entity.setOption3(dto.getOption3());
        entity.setOption4(dto.getOption4());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setExerciseEntity(new ExerciseBeanUtil().dto2Entity(dto.getExerciseDTO()));
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
