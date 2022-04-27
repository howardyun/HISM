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

    /**
     * 注册用户
     *
     * @param user
     * @return java.lang.String
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:05 下午
     */
    public String insertUser(User user) {
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            return "该用户已注册";
        }
        return "注册成功";
    }


    /**
     * 更新用户数据
     *
     * @param user
     * @return java.lang.String
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:06 下午
     */
    public String updateUser(User user) {
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "更新失败, 可能的原因,用户名重复";
        }
        return "更新成功";
    }


    /**
     * 通过用户名模糊查询用户
     *
     * @param name
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:06 下午
     */
    public List<User> selectUserbyName(String name) {
        //sql层面支持模糊查找
        List<User> userList = userMapper.selectUserbyName(name);
        if (userList.isEmpty()) {
            System.out.println("该用户不存在");
            return null;
        }
        return userList;
    }

    /**
     * 通过Id查询用户
     *
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:07 下午
     */
    public List<User> selectUserbyId(String userId) {
        List<User> userList = userMapper.selectUserbyId(userId);
        if (userList.isEmpty()) {
            System.out.println("该用户id不存在");
            return null;
        }
        return userList;
    }


    /**
     * 通过用户id获取他关注的所有人
     *
     * @param followerId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:08 下午
     */
    public List<User> getSubscriberByUserId(String followerId) {
        List<User> userList = userMapper.getSubscriberByUserId(followerId);
        if (userList.isEmpty()) {
            System.out.println("该用户不关注任何人");
            return userList;
        }
        return userList;
    }


    /**
     * 通过用户id获取所有他的粉丝.
     *
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:11 下午
     */
    public List<User> getFanByUserId(String userId) {
        List<User> fanList = userMapper.getFanByUserId(userId);
        if (fanList.isEmpty()) {
            System.out.println("该用户没有粉丝");
            return fanList;
        }
        return fanList;
    }

    /**
     * 获取所有用户数据
     *
     * @param
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:12 下午
     */
    public List<User> selectUserAll() {
        return userMapper.selectUserAll();
    }


    /**
     * 获取指定状态的用户
     *
     * @param userState
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 7:12 下午
     */
    public List<User> selectUserByState(int userState) {
        return userMapper.selectUserByState(userState);
    }

}
