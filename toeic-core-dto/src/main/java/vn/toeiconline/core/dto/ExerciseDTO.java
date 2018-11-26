package vn.toeiconline.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ExerciseDTO implements Serializable {
    private Integer exerciseId;
    private String name;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private ExerciseTypeDTO exerciseTypeDTO;

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

    public ExerciseTypeDTO getExerciseTypeDTO() {
        return exerciseTypeDTO;
    }

    public void setExerciseTypeDTO(ExerciseTypeDTO exerciseTypeDTO) {
        this.exerciseTypeDTO = exerciseTypeDTO;
    }
}
