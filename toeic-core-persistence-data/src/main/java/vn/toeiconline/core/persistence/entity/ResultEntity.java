package vn.toeiconline.core.persistence.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "result")
public class ResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resultId;

    @Column(name = "listenscore")
    private Integer listenScore;

    @Column(name = "readingscore")
    private Integer readingScore;

    @Column(name = "createdDate")
    private Timestamp createdDate;

    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;

    @ManyToOne
    @JoinColumn(name = "examinationid")
    private ExaminationEntity examinationEntity;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public Integer getListenScore() {
        return listenScore;
    }

    public void setListenScore(Integer listenScore) {
        this.listenScore = listenScore;
    }

    public Integer getReadingScore() {
        return readingScore;
    }

    public void setReadingScore(Integer readingScore) {
        this.readingScore = readingScore;
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

    public ExaminationEntity getExaminationEntity() {
        return examinationEntity;
    }

    public void setExaminationEntity(ExaminationEntity examinationEntity) {
        this.examinationEntity = examinationEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
