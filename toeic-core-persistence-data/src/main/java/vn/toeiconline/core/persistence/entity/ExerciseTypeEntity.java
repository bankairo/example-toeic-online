package vn.toeiconline.core.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "exercisetype")
public class ExerciseTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer exerciseTypeId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "createdDate")
    private Timestamp createdDate;

    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;

    @OneToMany(mappedBy = "exerciseTypeEntity", fetch = FetchType.LAZY)
    private List<ExerciseEntity> exerciseEntityList;

    public Integer getExerciseTypeId() {
        return exerciseTypeId;
    }

    public void setExerciseTypeId(Integer exerciseTypeId) {
        this.exerciseTypeId = exerciseTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public List<ExerciseEntity> getExerciseEntityList() {
        return exerciseEntityList;
    }

    public void setExerciseEntityList(List<ExerciseEntity> exerciseEntityList) {
        this.exerciseEntityList = exerciseEntityList;
    }
}
