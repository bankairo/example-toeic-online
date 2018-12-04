package vn.toeiconline.command;

import vn.toeiconline.core.dto.ExerciseDTO;
import vn.toeiconline.core.dto.ExerciseQuestionDTO;
import vn.toeiconline.core.dto.ExerciseQuestionImportDTO;
import vn.toeiconline.core.web.command.AbstractCommand;

import java.util.List;

public class ExerciseQuestionCommand extends AbstractCommand<ExerciseQuestionDTO> {
    public ExerciseQuestionCommand() {
        this.pojo = new ExerciseQuestionDTO();
    }
    private Integer exerciseId;
    private String answerUser;
    private boolean checkAnswer;
    private String exerciseNameSearch;
    private List<ExerciseDTO> exercises;
    private List<String> answerList;
    private List<ExerciseQuestionImportDTO> exerciseQuestionImportDTOS;

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getAnswerUser() {
        return answerUser;
    }

    public void setAnswerUser(String answerUser) {
        this.answerUser = answerUser;
    }

    public boolean isCheckAnswer() {
        return checkAnswer;
    }

    public void setCheckAnswer(boolean checkAnswer) {
        this.checkAnswer = checkAnswer;
    }

    public String getExerciseNameSearch() {
        return exerciseNameSearch;
    }

    public void setExerciseNameSearch(String exerciseNameSearch) {
        this.exerciseNameSearch = exerciseNameSearch;
    }

    public List<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public List<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<String> answerList) {
        this.answerList = answerList;
    }

    public List<ExerciseQuestionImportDTO> getExerciseQuestionImportDTOS() {
        return exerciseQuestionImportDTOS;
    }

    public void setExerciseQuestionImportDTOS(List<ExerciseQuestionImportDTO> exerciseQuestionImportDTOS) {
        this.exerciseQuestionImportDTOS = exerciseQuestionImportDTOS;
    }
}
