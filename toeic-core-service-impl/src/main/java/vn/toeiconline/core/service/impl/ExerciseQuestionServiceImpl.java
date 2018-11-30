package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.persistence.entity.ExerciseQuestionEntity;
import vn.toeiconline.core.service.ExerciseQuestionService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ExerciseQuestionBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseQuestionServiceImpl implements ExerciseQuestionService {
    public Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit) {
        List<ExerciseQuestionDTO> result = new ArrayList<ExerciseQuestionDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseQuestionDaoInstance().findByProperty( property, sortExpression, sortDirection, offset, limit, null);
        if (objects[1] != null) {
            for (ExerciseQuestionEntity item: (List<ExerciseQuestionEntity>)objects[1]) {
                ExerciseQuestionDTO dto = ExerciseQuestionBeanUtil.entity2Dto(item);
                result.add(dto);
            }
            objects[1] = result;
        }
        return objects;
    }
}
