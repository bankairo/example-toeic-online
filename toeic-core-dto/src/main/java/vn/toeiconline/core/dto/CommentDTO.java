package vn.toeiconline.core.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class CommentDTO implements Serializable {
    private Integer commentId;
    private String content;
    private Timestamp createdDate;
    private UserDTO userDTO;
    private ListenGuidelineDTO listenGuidelineDTO;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public ListenGuidelineDTO getListenGuidelineDTO() {
        return listenGuidelineDTO;
    }

    public void setListenGuidelineDTO(ListenGuidelineDTO listenGuidelineDTO) {
        this.listenGuidelineDTO = listenGuidelineDTO;
    }
}
