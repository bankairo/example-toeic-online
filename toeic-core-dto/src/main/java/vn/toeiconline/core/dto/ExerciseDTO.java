package vn.toeiconline.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class ExerciseDTO implements Serializable {
    private Integer exerciseId;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private String type;
    private List<ExerciseQuestionDTO> exerciseQuestionDTOList;

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ExerciseQuestionDTO> getExerciseQuestionDTOList() {
        return exerciseQuestionDTOList;
    }

    public void setExerciseQuestionDTOList(List<ExerciseQuestionDTO> exerciseQuestionDTOList) {
        this.exerciseQuestionDTOList = exerciseQuestionDTOList;
    }
}
