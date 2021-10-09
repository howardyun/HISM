package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.FollowerSerive;
import com.HISM.backfront.Service.GeneralService;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.swing.plaf.multi.MultiFileChooserUI;
import java.io.IOException;
import java.util.*;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    FollowerSerive followerSerive;

    @Resource
    GeneralService generalService;

    @Resource
    WebAppConfig webAppConfig;

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
                HashMap<String,Object> tmp=new HashMap<>();
                tmp.put("userID", userId);
                myResult.add("message",tmp);

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

    public MyResult register(@RequestParam("等同于手机号") String userId, @RequestParam String password,
                             @RequestParam String isMale, @RequestParam String userName) {
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
            user.setUserName(userName);
            user.setUserSex(isMale);
            userService.insertUser(user);
            myResult.changeStatus(true);
            HashMap<String,Object> tmp=new HashMap<>();
            tmp.put("userID", userId);
            myResult.add("message",tmp);
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
            myResult.add("message", "没有该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息重复");
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

    @PutMapping("/changeAvatar")

    @ApiOperation("修改头像")

    public MyResult changeAvatar(@RequestParam String userId, @RequestParam("editormd-image-file") MultipartFile multipartFile) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "userId为空");
            return myResult;
        }
        List<User> users = userService.queryUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "找不到该用户");
        } else {
            User user = users.get(0);
            //获取源文件名称
            String root_fileName = "userAvatar.png";
            //获取地址
            String filePath = webAppConfig.location + "/";
            filePath += user.getUserId();
            filePath += ("/" + "Avatar");
            String file_name = null;
            try {
                file_name = generalService.saveImg(multipartFile, filePath, root_fileName);
                user.setAvatarURL(filePath + "/" + root_fileName);
                userService.updateUser(user);
            } catch (IOException e) {
                myResult.changeStatus(false);
                myResult.add("message", "test");
                return myResult;
            }
        }
        myResult.changeStatus(true);
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
            myResult.add("message", "没有该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息冗余");
        } else {
            User user = users.get(0);
            if (passwordOld.equals(user.getPassword())) {
                user.setPassword(passwordNew);
                userService.updateUser(user);
                myResult.changeStatus(true);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "原密码不正确");
            }

        }
        return myResult;

    }

    @PostMapping("/getFans")

    @ApiOperation("获取粉丝列表")

    public MyResult getFans(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }

       List<User> users =userService.queryUserbyId(targetUserId);
        List<User> users1=userService.queryUserbyId(userId);
        if(users==null){
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        }
        else if (users.size()>1){
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        }else {
            if(users1==null){
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            }
            else if (users1.size()>1){
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            }else{

                List<User> userList= userService.getFanByUserId(targetUserId);
                followerSerive.getFollowState(userId,targetUserId);

            }

        }




        return myResult;
    }

    @PostMapping("/reportUser")

    @ApiOperation("举报用户")

    public MyResult reportUser(@RequestParam String userId, @RequestParam String targetUserId, @RequestParam String message) {
        MyResult myResult = new MyResult();
        return myResult;
    }

    @PostMapping("/followUser")

    @ApiOperation("关注用户")

    public MyResult followUser(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //这里需要加入查询
        Follower follower = new Follower();
        follower.setUserId(userId);
        follower.setFollowerId(targetUserId);
        followerSerive.insertFollower(follower);
        myResult.changeStatus(true);
        return myResult;

    }

    @PostMapping("/searchUser")

    @ApiOperation("搜索用户")

    public MyResult searchUser(@RequestParam String userId, @RequestParam String queryUserName) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(queryUserName)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，搜索名字不能为空");
            return myResult;
        }
        List<User> users = userService.queryUserbyName(queryUserName);
        if (users == null) {
            myResult.changeStatus(false);
        } else {
            myResult.changeStatus(true);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (int i = 0; i < users.size(); i++) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("userId", users.get(i).getUserId());
                map.put("userName", users.get(i).getUserName());
                map.put("userAvatar", users.get(i).getAvatarURL());
                map.put("relationship", null);
                tmp.add(map);
            }
            myResult.add("message",tmp);
        }
        return myResult;
    }

}
