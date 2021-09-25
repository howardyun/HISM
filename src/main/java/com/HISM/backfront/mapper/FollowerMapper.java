package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Follower;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FollowerMapper {
    // 关注某一用户
    public void insertFollower(Follower follower);
    // 取消关注某一用户
    public void deleteFollower(String followerId, String userId);
}
