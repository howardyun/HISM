package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    // 注册用户
    void insertUser(User user);

    // 更新用户数据
    void updateUser(User user);

    // 通过名字查询用户
    List<User> selectUserbyName(String name);

    // 通过Id查询用户
    List<User> selectUserbyId(String userId);

    // 获取被举报指定次数的用户信息
    public List<User> selectUserByTipOffNum(int tipOffNum);

    // 通过用户(粉丝)id获取他的所关注的人
    public List<User> getSubscriberByUserId(String followerId);

    // 通过用户id获取所有他的粉丝.
    public List<User> getFanByUserId(String userId);

    // 获取所有用户
    public List<User> selectUserAll();

    // 获取指定状态的用户
    public List<User> selectUserByState(int userState);

}
