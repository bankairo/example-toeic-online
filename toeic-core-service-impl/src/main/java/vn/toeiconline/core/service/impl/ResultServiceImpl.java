package vn.toeiconline.core.service.impl;

import vn.toeiconline.core.dto.ExaminationQuestionDTO;
import vn.toeiconline.core.dto.ResultDTO;
import vn.toeiconline.core.persistence.entity.ExaminationEntity;
import vn.toeiconline.core.persistence.entity.ResultEntity;
import vn.toeiconline.core.persistence.entity.UserEntity;
import vn.toeiconline.core.service.ResultService;
import vn.toeiconline.core.service.utils.SingletonDaoUtil;
import vn.toeiconline.core.utils.ResultBeanUtil;

import java.sql.Timestamp;
import java.util.List;

public class ResultServiceImpl implements ResultService {
    public ResultDTO save(String userName, Integer examinationId, List<ExaminationQuestionDTO> examinationQuestionDTOS) {

        UserEntity userEntity = SingletonDaoUtil.getUserDaoInstance().findEqualUnique("name", userName);
        ExaminationEntity examinationEntity = SingletonDaoUtil.getExaminationDaoInstance().findById(examinationId);
        ResultEntity resultEntity = new ResultEntity();
        calculateListenAndReadingScore(resultEntity, examinationQuestionDTOS);
        initDataToResultEntity(resultEntity, userEntity, examinationEntity);
        resultEntity = SingletonDaoUtil.getResultDaoInstance().save(resultEntity);
        return ResultBeanUtil.entity2Dto(resultEntity);
    }

    private void initDataToResultEntity(ResultEntity resultEntity, UserEntity userEntity, ExaminationEntity examinationEntity) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        resultEntity.setCreatedDate(timestamp);
        resultEntity.setUserEntity(userEntity);
        resultEntity.setExaminationEntity(examinationEntity);
    }

    private void calculateListenAndReadingScore(ResultEntity resultEntity, List<ExaminationQuestionDTO> examinationQuestionDTOS) {
        Integer listenScore = 0;
        Integer readingScore = 0;
        for (ExaminationQuestionDTO item : examinationQuestionDTOS) {
            if (item.getAnswerUser() != null) {
                if (item.getAnswerUser().equals(item.getCorrectAnswer())) {
                    if (item.getType().equals("PHOTO") || item.getType().equals("QUESTION_RESPONSE")) {
                        listenScore++;
                    } else if (item.getType().equals("SINGLE_PASSAGE")) {
                        readingScore++;
                    }
                }
            }
        }
        resultEntity.setListenScore(listenScore);
        resultEntity.setReadingScore(readingScore);
    }
}
