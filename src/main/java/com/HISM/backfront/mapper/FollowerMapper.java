package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FollowerMapper {
    // 关注某一用户
    public void insertFollower(Follower follower);
    // 取消关注某一用户
    public void deleteFollower(String followerId, String userId);

    public List<Follower> getFan(String userId);

    public List<Follower> getSubscriber(String userId);

}
