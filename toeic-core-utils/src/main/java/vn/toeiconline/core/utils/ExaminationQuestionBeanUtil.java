package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.ExaminationQuestionDTO;
import vn.toeiconline.core.persistence.entity.ExaminationQuestionEntity;

public class ExaminationQuestionBeanUtil {
    public static ExaminationQuestionDTO entity2Dto(ExaminationQuestionEntity entity) {
        ExaminationQuestionDTO dto = new ExaminationQuestionDTO();
        dto.setExaminationQuestionId(entity.getExaminationQuestionId());
        dto.setImage(entity.getImage());
        dto.setAudio(entity.getAudio());
        dto.setQuestion(entity.getQuestion());
        dto.setParagraph(entity.getParagraph());
        dto.setOption1(entity.getOption1());
        dto.setOption2(entity.getOption2());
        dto.setOption3(entity.getOption3());
        dto.setOption4(entity.getOption4());
        dto.setCorrectAnswer(entity.getCorrectAnswer());
        dto.setExamination(new ExaminationBeanUtil().entity2Dto(entity.getExaminationEntity()));
        dto.setType(entity.getType());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ExaminationQuestionEntity dto2Entity(ExaminationQuestionDTO dto) {
        ExaminationQuestionEntity entity = new ExaminationQuestionEntity();
        entity.setExaminationQuestionId(dto.getExaminationQuestionId());
        entity.setImage(dto.getImage());
        entity.setAudio(dto.getAudio());
        entity.setQuestion(dto.getQuestion());
        entity.setParagraph(dto.getParagraph());
        entity.setOption1(dto.getOption1());
        entity.setOption2(dto.getOption2());
        entity.setOption3(dto.getOption3());
        entity.setOption4(dto.getOption4());
        entity.setCorrectAnswer(dto.getCorrectAnswer());
        entity.setType(dto.getType());
        entity.setExaminationEntity(new ExaminationBeanUtil().dto2Entity(dto.getExamination()));
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
