package com.HISM.backfront.Service;

import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    // 注册用户
    public String insertUser(User user){
        try{
            userMapper.insertUser(user);
        }catch (Exception e){
            return "该用户已注册";
        }
        return "注册成功";
    }

    // 更新用户数据
    public String updateUser(User user){
        try{
            userMapper.updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
            return "更新失败, 可能的原因,用户名重复";
        }
        return "更新成功";
    }


    // 通过名字查询用户
    public List<User> queryUserbyName(String name){
        List<User> userList = userMapper.queryUserbyName(name);
        if(userList.isEmpty()) {
            System.out.println("该用户不存在");
            return null;
        }
        return userList;
    }

    // 通过Id查询用户
    public List<User> queryUserbyId(String userId){
        List<User> userList = userMapper.queryUserbyId(userId);
        if(userList.isEmpty()) {
            System.out.println("该用户id不存在");
            return null;
        }
        return userList;
    }

    // 通过id查询某一粉丝关注的用户列表
    public List<User> queryUserListByFollowerId(String followerId){
        List<User> userList = userMapper.queryUserListByFollowerId(followerId);
        if(userList.isEmpty()) {
            System.out.println("该粉丝id不存在");
            return null;
        }
        return userList;
    }


}
