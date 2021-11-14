package com.HISM.backfront.mapper;
import com.HISM.backfront.domain.TipOffUser;

import java.util.List;

public interface TipOffUserMapper {

    // 插入举报
    void insertTipOff(TipOffUser tipOffUser);

    // 修改举报数据
    void updateTipOff(TipOffUser tipOffUser);

    // 通过被举报的用户id获取举报数据
    List<TipOffUser> selectTipOffByUserId(String UserId);
}
