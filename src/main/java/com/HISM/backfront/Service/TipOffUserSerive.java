package com.HISM.backfront.Service;

import com.HISM.backfront.domain.TipOffDynamic;
import com.HISM.backfront.domain.TipOffUser;
import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.TipOffUserMapper;
import com.HISM.backfront.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TipOffUserSerive {
    @Resource
    TipOffUserMapper tipOffUserMapper;

    @Resource
    UserMapper userMapper;


    // 功能：通过用户id插入一次举报记录， 举报次数加一，判断是否需要封锁动态
    public int insertTipOff(TipOffUser tipOffUser) {
        User user = userMapper.selectUserbyId(tipOffUser.getUserId()).get(0);

        try {
            // 插入一次举报记录
            tipOffUserMapper.insertTipOff(tipOffUser);
        } catch (Exception e) {
            System.out.println("重复举报用户!!");
            return 2;
        }
        user.setTipOffNum(user.getTipOffNum() + 1);

        // 举报次数 >= 5 动态被封；
        if (user.getTipOffNum() >= 5) {
            user.setUserState(-1);
        }
        // 更新动态
        userMapper.updateUser(user);

        if (-1 == user.getUserState()) {
            // 返回1 用户被封.
            return 1;
        }
        // 返回0 正常举报
        return 0;

    }

    // 通过被举报的用户id获取举报数据
    public List<TipOffUser> selectTipOffByUserId(String userId) {
        List<TipOffUser> tipOffUserList = tipOffUserMapper.selectTipOffByUserId(userId);
        if (tipOffUserList.isEmpty()) {
            System.out.println("该用户没有被举报过");
        }
        return tipOffUserList;
    }

    // 使对该用户的举报无效  用户解封后使用
    public Boolean invalidateTipOff(String userId) {
        List<TipOffUser> tipOffUserList = selectTipOffByUserId(userId);
        try {
            for (int i = 0; i < tipOffUserList.size(); i++) {
                // 设置为0 使举报无效
                tipOffUserList.get(i).setIsValid(0);
                tipOffUserMapper.updateTipOff(tipOffUserList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
