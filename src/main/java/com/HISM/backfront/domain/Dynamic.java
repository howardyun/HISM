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
    String dynamicIndex;
    // 动态状态， -1动态被封；0动态暂时被封；1动态仅自己可见；2动态所有人可见
    int dynamicState;
    // 动态内容
    String dynamicContent;
    // 发布动态时间
    Date dynamicTime;
    // 点赞数量
    int thumbNum = 0;
    // 评论数量
    int commentNum = 0;
    // 举报数量
    int tipOffNum = 0;
    // 动态类型: 0-文本  1-图⽚  2-视频  3-GUI程序代码段  4-CLI程序代码段
    String dynamicType;
    // 该动态发出者的id
    String userId;

    // App 数据信息。
    private String language_;
    private String code;
    private String html;
    private String css;
    private String para;
    private String url;
    // 0-App被删， 1-App正常可访问
    private int appState;


    public Dynamic(String dynamicIndex, int dynamicState, String dynamicContent, Date dynamicTime, String dynamicType, String userId, String language_, String code, String html, String css, String para, String url, int appState) {
        this.dynamicIndex = dynamicIndex;
        this.dynamicState = dynamicState;
        this.dynamicContent = dynamicContent;
        this.dynamicTime = dynamicTime;
        this.dynamicType = dynamicType;
        this.userId = userId;
        this.language_ = language_;
        this.code = code;
        this.html = html;
        this.css = css;
        this.para = para;
        this.url = url;
        this.appState = appState;
    }

    public String getLanguage_() {
        return language_;
    }

    public void setLanguage_(String language_) {
        this.language_ = language_;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getAppState() {
        return appState;
    }

    public void setAppState(int appState) {
        this.appState = appState;
    }

    public int getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(int dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getDynamicIndex() {
        return dynamicIndex;
    }

    public void setDynamicIndex(String dynamicIndex) {
        this.dynamicIndex = dynamicIndex;
    }

    public int getDynamicState() {
        return dynamicState;
    }

    public void setDynamicState(int dynamicState) {
        this.dynamicState = dynamicState;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
