package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    /**
     * 注册用户
     * @param user
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:34 下午
     */
    void insertUser(User user);

    /**
     * 更新用户数据
     * @param user
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:34 下午
     */
    void updateUser(User user);

    /**
     * 通过名字查询用户
     * @param name
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:34 下午
     */
    List<User> selectUserbyName(String name);

    /**
     * 通过Id查询用户
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:34 下午
     */
    List<User> selectUserbyId(String userId);

    /**
     * 通过用户(粉丝)id获取他的所关注的人
     * @param followerId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:36 下午
     */
    public List<User> getSubscriberByUserId(String followerId);

    /**
     * 通过用户id获取所有他的粉丝
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:36 下午
     */
    public List<User> getFanByUserId(String userId);

    /**
     * 获取所有用户
     * @param
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:37 下午
     */
    public List<User> selectUserAll();

    /**
     * 获取指定状态的用户
     * @param userState
     * @return java.util.List<com.HISM.backfront.domain.User>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:37 下午
     */
    public List<User> selectUserByState(int userState);

}
