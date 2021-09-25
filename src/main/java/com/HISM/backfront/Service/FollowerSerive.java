package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.mapper.FollowerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FollowerSerive {
    @Resource
    FollowerMapper followerMapper;

    // 关注某一用户
    public boolean insertFollower(Follower follower){
        try{
            followerMapper.insertFollower(follower);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    // 取消关注某一用户
    public boolean deleteFollower(String followerId, String userId){
        try{
            followerMapper.deleteFollower(followerId, userId);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
