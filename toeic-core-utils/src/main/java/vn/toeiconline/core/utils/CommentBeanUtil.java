package vn.toeiconline.core.utils;

import vn.toeiconline.core.dto.CommentDTO;
import vn.toeiconline.core.persistence.entity.CommentEntity;

public class CommentBeanUtil {
    public static CommentDTO entity2Dto(CommentEntity entity) {
        CommentDTO dto = new CommentDTO();
        dto.setCommentId(entity.getCommentId());
        dto.setContent(entity.getContent());
        dto.setUserDTO(new UserBeanUtil().entity2Dto(entity.getUser()));
        dto.setListenGuidelineDTO(new ListenGuidelineBeanUtil().entity2Dto(entity.getListenGuidelineEntity()));
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
    public static CommentEntity dto2Entity(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setCommentId(dto.getCommentId());
        entity.setContent(dto.getContent());
        entity.setUser(new UserBeanUtil().dto2Entity(dto.getUserDTO()));
        entity.setListenGuidelineEntity(new ListenGuidelineBeanUtil().dto2Entity(dto.getListenGuidelineDTO()));
        entity.setCreatedDate(dto.getCreatedDate());
        return entity;
    }
}
