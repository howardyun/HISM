package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Follower;
import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.FollowerMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowerSerive {
    @Resource
    FollowerMapper followerMapper;


   /**
    * 关注某一用户
    * @param follower 
    * @return boolean
    * @author ysx
    * @creed: Talk is cheap,show me the code
    * @date 2022/4/27 6:43 下午
    */
    public boolean insertFollower(Follower follower) {
        try {
            followerMapper.insertFollower(follower);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 某一follower(粉丝) 取消关注某一user
     * @param followerId
     * @param userId
     * @return boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 6:43 下午
     */
    public boolean deleteFollower(String followerId, String userId) {
        try {
            followerMapper.deleteFollower(followerId, userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 获取粉丝关注情况
     *
     * @param userId1
     * @param userId2
     * @return int
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:51 下午
     */
    public int getFollowState(String userId1, String userId2) {
        //四个状态，12互为粉丝，12互相不关注，只有1关注2，只有2关注1
        Boolean user1FollowUser2 = false;
        Boolean user2FollowUser1 = false;

        List<Follower> followers = followerMapper.getSubscriber(userId1);
        for (Follower follower : followers) {
            //查看1是否关注2，是true，不是false
            if (follower.getUserId().equals(userId2)) {
                user1FollowUser2 = true;
            }
        }

        List<Follower> followerList = followerMapper.getSubscriber(userId2);
        for (Follower follower : followerList) {
            //查看2是否关注1，是true，不是false
            if (follower.getUserId().equals(userId1)) {
                user2FollowUser1 = true;
            }
        }

        if (user1FollowUser2 && user2FollowUser1) {
            //用户1与用户2相互关注
            return 3;
        } else if (user1FollowUser2 && (!user2FollowUser1)) {
            //"用户1仅关注用户2"
            return 1;
        } else if (!user1FollowUser2 && user2FollowUser1) {
            //"用户2仅关注用户1"
            return 2;
        }
        //"谁也不关注谁"
        return 0;
    }
}
