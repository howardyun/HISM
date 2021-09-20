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
    private String password;
    //用户姓名
    private String userName;
    //用户地址
    private String userAddress;
    //用户年龄
    private Integer userOld;
    //用户性别
    private String userSex;
    //用户描述
    private String userDescription;

}
