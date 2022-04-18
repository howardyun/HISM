package com.HISM.backfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    //评论id
    private int commentId;
    //评论时间
    private Date commentTime;
    //评论内容
    private String commentContent;
    //评论id
    private int dynamicId;
    //发出评论的用户id
    private String userId;

    public Comment(Date commentTime, String commentContent, int dynamicId, String userId){
        this.commentTime = commentTime;
        this.commentContent = commentContent;
        this.dynamicId = dynamicId;
        this.userId = userId;
    }

    //Getter and Setter
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
