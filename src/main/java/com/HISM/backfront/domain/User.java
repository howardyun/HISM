package com.HISM.backfront.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //用户Id
    private String userId;
    //用户密码
    private String password = "*****";
    //用户姓名
    private String userName;
    // 用户头像URL
    private String avatarURL;
    //用户地址
    private String userAddress;
    //用户年龄
    private Integer userOld;
    //用户性别
    private String userSex;
    //用户描述
    private String userDescription;
    //用户状态 -1用户账号被封；0用户账号暂时被封；1用户账号正常
    private Integer userState;
    //举报次数
    private Integer tipOffNum = 0;
    //用户感兴趣的动态index
    private String favorIndex;

    public String getFavorIndex() {
        return favorIndex;
    }

    public void setFavorIndex(String favorIndex) {
        this.favorIndex = favorIndex;
    }

    public Integer getTipOffNum() {
        return tipOffNum;
    }

    public void setTipOffNum(Integer tipOffNum) {
        this.tipOffNum = tipOffNum;
    }



    public User(String userId, String password, String userName, String userAddress, Integer userOld, String userSex, String userDescription, Integer userState, String favorIndex) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userOld = userOld;
        this.userSex = userSex;
        this.userDescription = userDescription;
        this.userState = userState;
        this.favorIndex = favorIndex;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public Integer getUserOld() {
        return userOld;
    }

    public void setUserOld(Integer userOld) {
        this.userOld = userOld;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public Integer getUserState() {
        return userState;
    }

    public void setUserState(Integer userState) {
        this.userState = userState;
    }

    public String getAvatarURL() { return avatarURL; }

    public void setAvatarURL(String avatarURL) { this.avatarURL = avatarURL; }
}
