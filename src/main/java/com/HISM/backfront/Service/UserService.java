package com.HISM.backfront.Service;

import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.UserMapper;
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
    public List<User> selectUserbyName(String name){
        List<User> userList = userMapper.selectUserbyName(name);
        if(userList.isEmpty()) {
            System.out.println("该用户不存在");
            return null;
        }
        return userList;
    }

    // 通过Id查询用户
    public List<User> selectUserbyId(String userId){
        List<User> userList = userMapper.selectUserbyId(userId);
        if(userList.isEmpty()) {
            System.out.println("该用户id不存在");
            return null;
        }
        return userList;
    }

    // 通过用户(粉丝)id获取他关注的所有人
    public List<User> getSubscriberByUserId(String followerId){
        List<User> userList = userMapper.getSubscriberByUserId(followerId);
        if(userList.isEmpty()) {
            System.out.println("该用户不关注任何人");
            return userList;
        }
        return userList;
    }


    // 通过用户id获取所有他的粉丝.
    public List<User> getFanByUserId(String userId){
        List<User> fanList = userMapper.getFanByUserId(userId);
        if(fanList.isEmpty()) {
            System.out.println("该用户没有粉丝");
            return fanList;
        }
        return fanList;
    }


    // 获取被举报指定次数的用户信息
    public List<User> selectUserByTipOffNum(int tipOffNum){
        if(0 > tipOffNum){
            System.out.println("err, 被举报次数为负数");
            return null;
        }
        return userMapper.selectUserByTipOffNum(tipOffNum);
    }

    // 获取所有用户
    public List<User> selectUserAll(){
        return userMapper.selectUserAll();
    }

    // 获取指定状态的用户
    public List<User> selectUserByState(int userState){
        return userMapper.selectUserByState(userState);
    }

}
