package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.FollowerSerive;
import com.HISM.backfront.Service.GeneralService;
import com.HISM.backfront.Service.TipOffUserSerive;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.domain.TipOffUser;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
@CrossOrigin("http://39.106.25.203")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    FollowerSerive followerSerive;

    @Resource
    GeneralService generalService;

    @Resource
    WebAppConfig webAppConfig;

    @Resource
    TipOffUserSerive tipOffUserSerive;

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
        List<User> userList = userService.selectUserbyId(userId);
        if (userList == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户信息");
        } else if (userList.size() == 1) {
            User user = userList.get(0);
            if (password.equals(user.getPassword())) {
                myResult.changeStatus(true);
                HashMap<String, Object> tmp = new HashMap<>();
                tmp.put("userID", userId);
                myResult.add("message", tmp);

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

    public MyResult register(@RequestParam String userId, @RequestParam String password,
                             @RequestParam String isMale, @RequestParam String userName) {
        MyResult myResult = new MyResult();

        if ("".equals(userId) || "".equals(password)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //判断userId是否已存在
        List<User> userList = userService.selectUserbyId(userId);
        if (userList == null) {
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setUserName(userName);
            user.setUserSex(isMale);
            userService.insertUser(user);
            myResult.changeStatus(true);
            HashMap<String, Object> tmp = new HashMap<>();
            tmp.put("userID", userId);
            myResult.add("message", tmp);
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
        List<User> users = userService.selectUserbyId(userId);

        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "申请者id不存在");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "申请者id多于一个");
        } else {
            List<User> userList = userService.selectUserbyId(targetUserId);
            if (userList == null) {
                myResult.changeStatus(false);
                myResult.add("message", "目标id不存在");

            } else if (userList.size() > 1) {

                myResult.changeStatus(false);
                myResult.add("message", "目标id多于一个");
            } else {
                User user = userList.get(0);
                myResult.changeStatus(true);
                List<User> tmp_FansList = userService.getFanByUserId(targetUserId);
                List<User> tmp_SubsList = userService.getSubscriberByUserId(targetUserId);
                Map<String, Object> map = new HashMap<>();
                map.put("userId", user.getUserId());
                map.put("userName", user.getUserName());
                map.put("userAvatar", user.getAvatarURL());
                map.put("follower", tmp_FansList.size());
                map.put("following", tmp_SubsList.size());
                map.put("isMale", user.getUserSex());
                map.put("description", user.getUserDescription());
                map.put("relationship", followerSerive.getFollowState(userId, targetUserId));
                myResult.add("message", map);
            }
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
        List<User> users = userService.selectUserbyId(userId);
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

    @PostMapping("/changeAvatar")

    @ApiOperation("修改头像")

    public MyResult changeAvatar(@RequestParam String userId, @RequestParam("editormd-image-file") MultipartFile multipartFile) {
        MyResult myResult = new MyResult();
        if ("".equals(userId)) {
            myResult.changeStatus(false);
            myResult.add("message", "userId为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "找不到该用户");
        } else {
            User user = users.get(0);
            //获取源文件名称
            System.out.println(multipartFile.getContentType());
            String name = multipartFile.getOriginalFilename();
            assert name != null;
            String []s=name.split("\\.");
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamp = new Timestamp(date.getTime());
            String root_fileName = "userAvatar" + "-" + timeStamp + "."+s[s.length-1];
            //获取地址
            String filePath = webAppConfig.location + "/";
            filePath += user.getUserId();
            filePath += ("/" + "Avatar");
            String file_name = null;
            try {
                file_name = generalService.saveImg(multipartFile, filePath, root_fileName);
                user.setAvatarURL("http://39.106.25.203/img/" + userId + "/Avatar/" + root_fileName);
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
        List<User> users = userService.selectUserbyId(userId);
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

        List<User> users = userService.selectUserbyId(targetUserId);
        List<User> users1 = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {

                List<User> userList = userService.getFanByUserId(targetUserId);
                List<Map<String, Object>> tmp = new ArrayList<>();

                for (int i = 0; i < userList.size(); i++) {
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("userId", userList.get(i).getUserId());
                    map.put("userName", userList.get(i).getUserName());
                    map.put("userAvatar", userList.get(i).getAvatarURL());
                    map.put("relationship", followerSerive.getFollowState(userId, targetUserId));
                    tmp.add(map);
                }
                myResult.add("message", tmp);
            }
        }
        return myResult;
    }

    @PostMapping("/getFollowers")

    @ApiOperation("获取关注列表")

    public MyResult getFollowers(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(targetUserId);
        List<User> users1 = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {

                List<User> userList = userService.getSubscriberByUserId(targetUserId);
                List<Map<String, Object>> tmp = new ArrayList<>();
                for (int i = 0; i < userList.size(); i++) {
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("userId", userList.get(i).getUserId());
                    map.put("userName", userList.get(i).getUserName());
                    map.put("userAvatar", userList.get(i).getAvatarURL());
                    map.put("relationship", followerSerive.getFollowState(userId, targetUserId));
                    tmp.add(map);
                }
                myResult.add("message", tmp);
            }
        }
        return myResult;
    }

    @PostMapping("/reportUser")

    @ApiOperation("举报用户")

    public MyResult reportUser(@RequestParam String userId, @RequestParam String targetUserId, @RequestParam String message) {
        MyResult myResult = new MyResult();
        List<User> users = userService.selectUserbyId(targetUserId);
        List<User> users1 = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "举报者id为空");
            } else if (users1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "举报者id不唯一");
            } else {
                if (users.get(0).getUserState() != 1) {
                    myResult.changeStatus(false);
                    myResult.add("message", "当前被举报用户已经被封");
                } else {

                    List<TipOffUser> tipOffUsers = tipOffUserSerive.selectTipOffByUserId(targetUserId);
                    if (tipOffUsers.size() >= 5) {
                        users.get(0).setUserState(0);
                        userService.updateUser(users.get(0));
                        myResult.changeStatus(false);
                        myResult.add("message", "举报数大于等于5但是没有封禁，目前已经封禁但是本次举报无效");
                    } else {
                        TipOffUser tipOffUser = new TipOffUser();
                        tipOffUser.setUserId(targetUserId);
                        tipOffUser.setInformerId(userId);
                        tipOffUser.setIsValid(1);
                        tipOffUser.setTipOffContent(message);
                        Date date = new Date(System.currentTimeMillis());
                        Timestamp timeStamep = new Timestamp(date.getTime());
                        tipOffUser.setTipOffTime(timeStamep);
                        tipOffUserSerive.insertTipOff(tipOffUser);
                        if (tipOffUsers.size() == 4) {
                            users.get(0).setUserState(0);
                            userService.updateUser(users.get(0));
                        }
                        myResult.changeStatus(true);
                    }
                }
            }
        }
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
        List<User> users = userService.selectUserbyId(targetUserId);
        List<User> users1 = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");
        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {

                int i = followerSerive.getFollowState(userId, targetUserId);
                if (i == 1 || i == 3) {
                    myResult.changeStatus(false);
                    myResult.add("message", "已经关注，无需重新关注");
                } else {
                    Follower follower = new Follower();
                    follower.setUserId(targetUserId);
                    follower.setFollowerId(userId);
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timeStamep = new Timestamp(date.getTime());
                    follower.setFollowTime(timeStamep);
                    followerSerive.insertFollower(follower);
                    myResult.changeStatus(true);
                }
            }
        }
        return myResult;

    }

    @PostMapping("/cancelFollowUser")
    @ApiOperation("取消关注用户")
    public MyResult cancelFollowUser(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //这里需要加入查询
        List<User> users = userService.selectUserbyId(targetUserId);
        List<User> users1 = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id为空");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");
        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {

                int i = followerSerive.getFollowState(userId, targetUserId);
                if (i == 0 || i == 2) {
                    myResult.changeStatus(false);
                    myResult.add("message", "没有关注，无需重复关注");
                } else {
                    Follower follower = new Follower();
                    follower.setUserId(userId);
                    follower.setFollowerId(targetUserId);
                    Date date = new Date(System.currentTimeMillis());
                    Timestamp timeStamep = new Timestamp(date.getTime());
                    follower.setFollowTime(timeStamep);
                    followerSerive.deleteFollower(userId, targetUserId);
                    myResult.changeStatus(true);
                }
            }
        }
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
        List<User> users = userService.selectUserbyName(queryUserName);
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
                map.put("relationship", followerSerive.getFollowState(userId, users.get(i).getUserId()));
                tmp.add(map);
            }
            myResult.add("message", tmp);
        }
        return myResult;
    }

}
