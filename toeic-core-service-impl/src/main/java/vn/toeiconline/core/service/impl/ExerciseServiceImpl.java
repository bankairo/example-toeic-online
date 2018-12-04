package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dto.ExerciseDTO;
import vn.toeiconline.core.persistence.entity.ExerciseEntity;
import vn.toeiconline.core.service.ExerciseService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ExerciseBeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExerciseServiceImpl implements ExerciseService {
    public Object[] findExerciseByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause) {
        List<ExerciseDTO> exerciseDTOS = new ArrayList<ExerciseDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseDaoInstance().findByProperty( property, sortExpression, sortDirection, offset, limit, whereClause);
        if (objects[1] != null) {
            for (ExerciseEntity item: (List<ExerciseEntity>)objects[1]) {
                ExerciseDTO dto = ExerciseBeanUtil.entity2Dto(item);
                exerciseDTOS.add(dto);
            }
            objects[1] = exerciseDTOS;
        }
        return objects;
    }

    public List<ExerciseDTO> findAll() {
        List<ExerciseEntity> exerciseEntities = SingletonDaoUtil.getExerciseDaoInstance().findAll();
        List<ExerciseDTO> exerciseDTOS = new ArrayList<ExerciseDTO>();
        for (ExerciseEntity item: exerciseEntities) {
            ExerciseDTO dto = ExerciseBeanUtil.entity2Dto(item);
            exerciseDTOS.add(dto);
        }
        return exerciseDTOS;
    }
}
