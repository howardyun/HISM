package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.FollowerSerive;
import com.HISM.backfront.Service.GeneralService;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.Tools.RsaTool;
import com.HISM.backfront.domain.Follower;
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

    @PostMapping("/logIn")
    //必填
    @ApiOperation("用户登陆")
    public MyResult logIn(@RequestParam String userId, @RequestParam String password) throws Exception {
        password=password.replace(" ","+");
        //注册Result对象
        MyResult myResult = new MyResult();
        //判断用户id或者密码是否为空
        if ("".equals(userId) || "".equals(password)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id或用户密码为空");
            return myResult;
        }
        //通过用户服务查看该id是否存在
        List<User> userList = userService.selectUserbyId(userId);
        //不存在
        if (userList == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户信息");
        } else if (userList.size() == 1) {
            User user = userList.get(0);
            //验证密码
            if (RsaTool.decryptByPrivateKey(RsaTool.privateKey,password).equals(user.getPassword())) {
                myResult.changeStatus(true);
                HashMap<String, Object> tmp = new HashMap<>();
                tmp.put("userID", userId);
                myResult.add("message", tmp);

            } else {
                //密码错误
                myResult.changeStatus(false);
                myResult.add("message", "密码错误");
            }
        } else {
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "用户信息多于一个");
        }
        return myResult;
    }

    @PostMapping("/register")

    @ApiOperation("用户注册")

    public MyResult register(@RequestParam String userId, @RequestParam String password,
                             @RequestParam String isMale, @RequestParam String userName, @RequestParam String email) {
        MyResult myResult = new MyResult();
        //查看输入进来的用户id或者密码是否为空
        if ("".equals(userId) || "".equals(password)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //判断userId是否已存在
        List<User> userList = userService.selectUserbyId(userId);
        if (userList == null) {
            //注册对象，填入信息
            User user = new User();
            user.setUserId(userId);
            user.setPassword(password);
            user.setUserName(userName);
            user.setUserSex(isMale);
            user.setUserState(1);
            user.setEmail(email);
            userService.insertUser(user);
            myResult.changeStatus(true);
            HashMap<String, Object> tmp = new HashMap<>();
            tmp.put("userID", userId);
            myResult.add("message", tmp);
        } else {
            //账户已经存在
            myResult.changeStatus(false);
            myResult.add("message", "账户已经存在");
        }
        return myResult;
    }

    @PostMapping("/retrievePassword")
    //必填
    @ApiOperation("找回密码")
    public MyResult retrievePassword(@RequestParam String userId, @RequestParam String email) {
        MyResult myResult = new MyResult();
        //确保传入进来的id或者邮箱不为空
        if ("".equals(userId) || "".equals(email)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/邮箱不能为空");
            return myResult;
        }
        List<User> userList = userService.selectUserbyId(userId);
        //没有该用户
        if (userList == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户信息");
        } else if (userList.size() == 1) {
            //验证邮件
            if (userList.get(0).getEmail().equals(email)) {
                myResult.changeStatus(true);
                myResult.add("message", userList.get(0).getPassword());
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "邮箱信息错误");
            }

        } else {
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "用户信息冗余");
        }

        return myResult;

    }


    @PostMapping("/getUsersInfo")
    @ApiOperation("获取某用户信息")
    public MyResult getUsersInfo(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        //确保传入的发起者id和被查看者id都不为空
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //确保发起者id是存在的
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "申请者id不存在");
        } else if (users.size() > 1) {
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "申请者id多于一个");
        } else {
            //确保被查看这id存在
            List<User> userList = userService.selectUserbyId(targetUserId);
            if (userList == null) {
                myResult.changeStatus(false);
                myResult.add("message", "目标id不存在");
            } else if (userList.size() > 1) {
                //数据库错误
                myResult.changeStatus(false);
                myResult.add("message", "目标id多于一个");
            } else {
                User user = userList.get(0);
                myResult.changeStatus(true);
                //获取关注和粉丝数量
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
        //确保传入的账号/密码/描述不为空
        if ("".equals(userId) || "".equals(userName) || "".equals(description)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码/描述不能为空");
            return myResult;
        }
        //查看用户是否存在
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息重复");
        } else {
            //更新用户信息
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
        //确保传入的用户id不为空
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
            String name = multipartFile.getOriginalFilename();
            //确保用户名不为空
            assert name != null;
            //打上我们定义的用户名称
            String[] s = name.split("\\.");
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamp = new Timestamp(date.getTime());
            String root_fileName = "userAvatar" + "-" + timeStamp + "." + s[s.length - 1];
            //获取地址
            String filePath = user.getUserId();
            filePath += ("/" + "Avatar");
            String file_name = null;
            //我们定义用户头像的名称为/userid/Avatar/userAvatar-上传时间.文件类型
            try {
                Map<String, String> t = new HashMap<String, String>();
                t.put("path", filePath);
                t.put("token", "123");
                t.put("fileName", root_fileName);
                //交由文件服务
                file_name = generalService.saveImg(multipartFile, t);
                if (file_name == null) {
                    myResult.changeStatus(false);
                    myResult.add("message", "文件存储失败");
                    return myResult;
                }
                user.setAvatarURL("http://39.106.25.203" + file_name);
                //更新用户信息
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
        //确保传入的账号/密码不为空
        if ("".equals(userId) || "".equals(passwordNew) || "".equals(passwordOld)) {
            myResult.changeStatus(false);
            myResult.add("message", "账号/密码不能为空");
            return myResult;
        }
        //确保新老密码不相同
        if (passwordOld.equals(passwordNew)) {
            myResult.changeStatus(false);
            myResult.add("message", "新旧密码相同");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有该用户");
        } else if (users.size() > 1) {
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "用户信息冗余");
        } else {
            User user = users.get(0);
            //保证用户知道用户原密码
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
        //确保传入账户和密码不为空
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
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                //数据库错误
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {
                //获取粉丝用户
                List<User> userList = userService.getFanByUserId(targetUserId);
                List<Map<String, Object>> tmp = new ArrayList<>();
                //录入信息
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
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");

        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                //数据库错误
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {
                //拉取关注者信息
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

    @PostMapping("/followUser")

    @ApiOperation("关注用户")

    public MyResult followUser(@RequestParam String userId, @RequestParam String targetUserId) {
        MyResult myResult = new MyResult();
        //确保传入的用户id不为空
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
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");
        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                //数据库错误
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {

                int i = followerSerive.getFollowState(userId, targetUserId);
                if (i == 1 || i == 3) {
                    //1代表已经关注，3代表互相关注
                    myResult.changeStatus(false);
                    myResult.add("message", "已经关注，无需重新关注");
                } else {
                    //插入关注
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
        //确保传入的用户id不为空
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
            //数据库错误
            myResult.changeStatus(false);
            myResult.add("message", "目标id不唯一");
        } else {
            if (users1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "查询者id为空");
            } else if (users1.size() > 1) {
                //数据库错误
                myResult.changeStatus(false);
                myResult.add("message", "查询者id不唯一");
            } else {
                int i = followerSerive.getFollowState(userId, targetUserId);
                if (i == 0 || i == 2) {
                    //0代表双方都没有关注彼此，2代表被取消者关注了发起取消者
                    myResult.changeStatus(false);
                    myResult.add("message", "没有关注，无需重复关注");
                } else {
                    //将关注关系从表中删除
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
        //确保传入的用户id以及查询的名字都是非空
        if ("".equals(userId) || "".equals(queryUserName)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，搜索名字不能为空");
            return myResult;
        }
        //从sql层面支持模糊查找
        List<User> users = userService.selectUserbyName(queryUserName);
        if (users == null) {
            myResult.changeStatus(false);
        } else {
            myResult.changeStatus(true);
            List<Map<String, Object>> tmp = new ArrayList<>();
            //录入信息
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

    @PostMapping("/getPublicKey")
    @ApiOperation("获取公钥")
    public MyResult getPublicKey(@RequestParam String userId) {
        MyResult myResult = new MyResult();
        if (RsaTool.publicKey != null && RsaTool.publicKey != "") {
            myResult.changeStatus(true);
            myResult.add("message", RsaTool.publicKey);
        }else {
            myResult.changeStatus(false);
            myResult.add("message", "error");
        }
        return myResult;
    }
}
