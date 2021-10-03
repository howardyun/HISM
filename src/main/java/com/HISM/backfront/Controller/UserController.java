package com.HISM.backfront.Controller;

import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.swing.plaf.multi.MultiFileChooserUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/logIn")
    //必填
    @ApiOperation("用户登陆")
    public MyResult logIn(@RequestParam String userId, @RequestParam String password) {

        MyResult myResult = new MyResult();
        //判断用户id或者密码是否为空
        if ("".equals(userId) || "".equals(password)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id或用户密码为空");
            return myResult;
        }
        List<User> userList = userService.queryUserbyId(userId);
        if (userList.size() == 0) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户信息");
        } else if (userList.size() == 1) {
            User user = userList.get(0);
            if (password.equals(user.getPassword())) {
                myResult.changeStatus(true);
                myResult.add("userID", userId);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "密码错误");
            }
        } else {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息多于一个");
        }
        return myResult;
    }

    @PostMapping("/register")

    @ApiOperation("用户注册")

    public MyResult register(@RequestParam String userId, @RequestParam String password) {
        MyResult myResult = new MyResult();

        if ("".equals(userId) || "".equals(password)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //判断userId是否已存在
        List<User> userList = userService.queryUserbyId(userId);
        if (userList.isEmpty()) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            userService.insertUser(user);
            myResult.changeStatus(true);
            myResult.add("userID", userId);
        } else {
            myResult.changeStatus(false);
            myResult.add("message", "账户已经存在");
        }
        return myResult;
    }


    //to be done
    @PostMapping("/getUsersInfo")

    @ApiOperation("获取某用户信息")

    public MyResult getUsersInfo(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }


        return myResult;
    }

    @PostMapping("/changeUsersInfo")

    @ApiOperation("修改用户信息")

    public MyResult changeUsersInfo(@RequestParam String userId, @RequestParam String userName,
                                    @RequestParam String isMale, @RequestParam String description) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(userName) || "".equals(description)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        List<User> users = userService.queryUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("reason", "没有该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("reason", "用户信息重复");
        } else {
            User user = users.get(0);
            user.setUserSex(isMale);
            user.setUserName(userName);
            user.setUserDescription(description);
            System.out.print(user);
            userService.updateUser(user);

            myResult.changeStatus(true);
        }

        return myResult;
    }

    @PostMapping("/changeAvatar")

    @ApiOperation("修改头像")

    public MyResult changeAvatar(@RequestParam String userId, @RequestParam MultiFileChooserUI img) {
        MyResult myResult = new MyResult();

        return myResult;

    }

    @PostMapping("/changePassword")

    @ApiOperation("修改密码")

    public MyResult changePassword(@RequestParam String userId, @RequestParam String passwordOld, @RequestParam String passwordNew) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(passwordNew) || "".equals(passwordOld)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        List<User> users = userService.queryUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("reason", "没有该用户");
        }else if(users.size()>1){
            myResult.changeStatus(false);
            myResult.add("reason", "用户信息冗余");
        }else{
            User user=users.get(0);
            if(passwordOld.equals(user.getPassword())){


            }


        }

        return myResult;

    }


}
