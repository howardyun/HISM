package com.HISM.backfront.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dynamic {
    // 动态Id
    int dynamicId;
    // 动态标签, ex: 美食, 金融
    String dynamicIndex1;
    String dynamicIndex2;
    // 动态访问限制
    int dynamicAccess;
    // 动态内容
    String dynamicContent;
    // 发布动态时间
    Date dynamicTime;
    // 点赞数量
    int thumbNum;
    // 评论数量
    int commentNum;
    // 举报数量
    int tipOffNum;
    // 动态类型: 图片, 小程序, 视频
    String dynamicType;
    // 该动态发出者的id
    String userName;


    public Dynamic(String dynamicIndex1, String dynamicIndex2, int dynamicAccess, String dynamicContent, Date dynamicTime, int thumbNum, int commentNum, int tipOffNum, String dynamicType, String userName) {
        this.dynamicIndex1 = dynamicIndex1;
        this.dynamicIndex2 = dynamicIndex2;
        this.dynamicAccess = dynamicAccess;
        this.dynamicContent = dynamicContent;
        this.dynamicTime = dynamicTime;
        this.thumbNum = thumbNum;
        this.commentNum = commentNum;
        this.tipOffNum = tipOffNum;
        this.dynamicType = dynamicType;
        this.userName = userName;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicIndex1() {
        return dynamicIndex1;
    }

    public void setDynamicIndex1(String dynamicIndex1) {
        this.dynamicIndex1 = dynamicIndex1;
    }

    public String getDynamicIndex2() {
        return dynamicIndex2;
    }

    public void setDynamicIndex2(String dynamicIndex2) {
        this.dynamicIndex2 = dynamicIndex2;
    }

    public int getDynamicAccess() {
        return dynamicAccess;
    }

    public void setDynamicAccess(int dynamicAccess) {
        this.dynamicAccess = dynamicAccess;
    }

    public String getDynamicContent() {
        return dynamicContent;
    }

    public void setDynamicContent(String dynamicContent) {
        this.dynamicContent = dynamicContent;
    }

    public Date getDynamicTime() {
        return dynamicTime;
    }

    public void setDynamicTime(Date dynamicTime) {
        this.dynamicTime = dynamicTime;
    }

    public int getThumbNum() {
        return thumbNum;
    }

    public void setThumbNum(int thumbNum) {
        this.thumbNum = thumbNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getTipOffNum() {
        return tipOffNum;
    }

    public void setTipOffNum(int tipOffNum) {
        this.tipOffNum = tipOffNum;
    }

    public String getDynamicType() {
        return dynamicType;
    }

    public void setDynamicType(String dynamicType) {
        this.dynamicType = dynamicType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
