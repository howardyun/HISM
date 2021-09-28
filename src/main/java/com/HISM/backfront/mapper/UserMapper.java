package com.HISM.backfront.mapper;

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
    List<User> queryUserbyName(String name);

    // 通过Id查询用户
    List<User> queryUserbyId(String userId);

    // 获取用户数量
    int getUserNum();


    // 通过id查询某一粉丝关注的用户列表
    public List<User> queryUserListByFollowerId(String followerId);
}
