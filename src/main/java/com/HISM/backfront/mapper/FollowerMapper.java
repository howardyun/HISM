package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FollowerMapper {
    /**
     * 关注某一用户
     * @param follower
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:31 下午
     */
    public void insertFollower(Follower follower);

    /**
     * 取消关注某一用户
     * @param followerId
	 * @param userId
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:31 下午
     */
    public void deleteFollower(String followerId, String userId);

    /**
     * 获取粉丝列表
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.Follower>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:31 下午
     */
    public List<Follower> getFan(String userId);

    /**
     * 获取关注列表
     * @param userId
     * @return java.util.List<com.HISM.backfront.domain.Follower>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:32 下午
     */
    public List<Follower> getSubscriber(String userId);

}
