package vn.toeiconline.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class ResultDTO implements Serializable {
    private Integer resultId;
    private Integer listenScore;
    private Integer readingScore;
    private Timestamp createdDate;
    private Timestamp modifiedDate;
    private ExaminationDTO examinationDTO;
    private UserDTO userDTO;

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

    public ExaminationDTO getExaminationDTO() {
        return examinationDTO;
    }

    public void setExaminationDTO(ExaminationDTO examinationDTO) {
        this.examinationDTO = examinationDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
