package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.ResultDTO;
import vn.toeiconline.core.persistence.entity.ResultEntity;

public class ResultBeanUtil {
    public static ResultDTO entity2Dto(ResultEntity entity) {
        ResultDTO dto = new ResultDTO();
        dto.setResultId(entity.getResultId());
        dto.setListenScore(entity.getListenScore());
        dto.setReadingScore(entity.getReadingScore());
        dto.setExaminationDTO(new ExaminationBeanUtil().entity2Dto(entity.getExaminationEntity()));
        dto.setUserDTO(new UserBeanUtil().entity2Dto(entity.getUserEntity()));
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setModifiedDate(entity.getModifiedDate());
        return dto;
    }
    public static ResultEntity dto2Entity(ResultDTO dto) {
        ResultEntity entity = new ResultEntity();
        entity.setResultId(dto.getResultId());
        entity.setListenScore(dto.getListenScore());
        entity.setReadingScore(dto.getReadingScore());
        entity.setExaminationEntity(new ExaminationBeanUtil().dto2Entity(dto.getExaminationDTO()));
        entity.setUserEntity(new UserBeanUtil().dto2Entity(dto.getUserDTO()));
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setModifiedDate(dto.getModifiedDate());
        return entity;
    }
}
