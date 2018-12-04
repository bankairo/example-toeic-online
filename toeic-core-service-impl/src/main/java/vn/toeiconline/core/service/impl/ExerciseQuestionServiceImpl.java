package vn.toeiconline.core.service.impl;

import org.apache.commons.lang.StringUtils;
import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.dto.ExerciseQuestionImportDTO;
import vn.toeiconline.core.persistence.entity.ExerciseEntity;
import vn.toeiconline.core.persistence.entity.ExerciseQuestionEntity;
import vn.toeiconline.core.service.ExerciseQuestionService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ExerciseQuestionBeanUtil;

import java.sql.Timestamp;
import java.util.*;

public class ExerciseQuestionServiceImpl implements ExerciseQuestionService {
    public Object[] findExerciseQuestionByProperties(Map<String, Object> property, String sortExpression, String sortDirection, Integer offset, Integer limit, String whereClause) {
        List<ExerciseQuestionDTO> result = new ArrayList<ExerciseQuestionDTO>();
        Object[] objects = SingletonDaoUtil.getExerciseQuestionDaoInstance().findByProperty( property, sortExpression, sortDirection, offset, limit, whereClause);
        if (objects[1] != null) {
            for (ExerciseQuestionEntity item: (List<ExerciseQuestionEntity>)objects[1]) {
                ExerciseQuestionDTO dto = ExerciseQuestionBeanUtil.entity2Dto(item);
                result.add(dto);
            }
            objects[1] = result;
        }
        return objects;
    }

    public ExerciseQuestionDTO findExerciseQuestionById(Integer id) {
        ExerciseQuestionEntity entity = SingletonDaoUtil.getExerciseQuestionDaoInstance().findById(id);
        ExerciseQuestionDTO dto = ExerciseQuestionBeanUtil.entity2Dto(entity);
        return dto;
    }

    public void save(ExerciseQuestionDTO dto) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dto.setCreatedDate(timestamp);
        ExerciseQuestionEntity entity = ExerciseQuestionBeanUtil.dto2Entity(dto);
        setOptions(entity);
        SingletonDaoUtil.getExerciseQuestionDaoInstance().save(entity);
    }

    public ExerciseQuestionDTO update(ExerciseQuestionDTO dto) {
        Timestamp modifiedDate = new Timestamp(System.currentTimeMillis());
        dto.setModifiedDate(modifiedDate);
        ExerciseQuestionEntity entity = ExerciseQuestionBeanUtil.dto2Entity(dto);
        setOptions(entity);
        entity = SingletonDaoUtil.getExerciseQuestionDaoInstance().update(entity);
        dto = ExerciseQuestionBeanUtil.entity2Dto(entity);
        return dto;
    }

    public Integer delete(List<Integer> ids) {
        Integer result = SingletonDaoUtil.getExerciseQuestionDaoInstance().delete(ids);
        return result;
    }

    public void validateImportExerciseQuestionImport(List<ExerciseQuestionImportDTO> importDTOS) {
        Set<String> exerciseNameSet = new HashSet<String>();
        for (ExerciseQuestionImportDTO item: importDTOS) {
            exerciseNameSet.add(item.getExerciseName());
        }
        List<Object> exerciseNameList = new ArrayList<Object>(exerciseNameSet);
        List<ExerciseEntity> exerciseEntities = SingletonDaoUtil.getExerciseDaoInstance().findWhereIn("name", exerciseNameList);

        if (exerciseEntities.size() > 0) {
            exerciseNameSet.clear();
            for (ExerciseEntity item: exerciseEntities) {
                exerciseNameSet.add(item.getName().toLowerCase());
            }
            for (ExerciseQuestionImportDTO item: importDTOS) {
                if (item.isValid()) {
                    String message = "";
                    if (!exerciseNameSet.contains(item.getExerciseName().toLowerCase())) {
                        message += "<br/>";
                        message += "Tên bài tập không tồn tại";
                    }
                    if (StringUtils.isNotBlank(message)) {
                        item.setValid(false);
                        item.setMessage(message.substring(5));
                    }
                }
            }
        }
    }

    public void saveImportExerciseQuestionList(List<ExerciseQuestionImportDTO> importDTOS) {
        for (ExerciseQuestionImportDTO item: importDTOS) {
            if (item.isValid()) {
                ExerciseQuestionEntity entity = new ExerciseQuestionEntity();
                entity.setQuestion(item.getQuestion());
                entity.setImage(item.getImage());
                entity.setAudio(item.getAudio());
                entity.setCorrectAnswer(item.getCorrectAnswer());
                ExerciseEntity exerciseEntity = SingletonDaoUtil.getExerciseDaoInstance().findEqualUnique("name", item.getExerciseName());
                entity.setExerciseEntity(exerciseEntity);
                setOptions(entity);
                Timestamp createdDate = new Timestamp(System.currentTimeMillis());
                entity.setCreatedDate(createdDate);
                SingletonDaoUtil.getExerciseQuestionDaoInstance().save(entity);
            }
        }
    }

    private void setOptions(ExerciseQuestionEntity entity) {
        entity.setOption1("A");
        entity.setOption2("B");
        entity.setOption3("C");
        entity.setOption4("D");
    }
}
