package vn.toeiconline.core.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "exercise")
public class ExerciseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exerciseId;

    @Column(name = "name")
    private String name;

    @Column(name = "createdDate")
    private Timestamp createdDate;

    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;

    @Column(name = "type")
    private String type;

    @OneToMany(mappedBy = "exerciseEntity", fetch = FetchType.LAZY)
    private List<ExerciseQuestionEntity> exerciseQuestionEntityList;

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

    public List<ExerciseQuestionEntity> getExerciseQuestionEntityList() {
        return exerciseQuestionEntityList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExerciseQuestionEntityList(List<ExerciseQuestionEntity> exerciseQuestionEntityList) {
        this.exerciseQuestionEntityList = exerciseQuestionEntityList;
    }
}
