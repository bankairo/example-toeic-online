package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dto.ExaminationQuestionDTO;
import vn.toeiconline.core.persistence.entity.ExaminationQuestionEntity;
import vn.toeiconline.core.service.ExaminationQuestionService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ExaminationQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExaminationQuestionServiceImpl implements ExaminationQuestionService {
    public Object[] findExaminationQuestionByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, Integer examinationId) {
        List<ExaminationQuestionDTO> dtos = new ArrayList<ExaminationQuestionDTO>();
        String whereClause = " AND examinationid = " + examinationId + " ";
        Object[] objects = SingletonDaoUtil.getExaminationQuestionDaoInstance().findByProperty(property, sortExpression, sortDirection, offset, limit, whereClause);
        for (ExaminationQuestionEntity item: (List<ExaminationQuestionEntity>)objects[1]) {
            ExaminationQuestionDTO dto = ExaminationQuestionBeanUtil.entity2Dto(item);
            dtos.add(dto);
        }
        objects[1] = dtos;
        return objects;
    }
}
