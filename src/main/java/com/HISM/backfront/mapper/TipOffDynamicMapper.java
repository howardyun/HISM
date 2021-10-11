package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.TipOffDynamic;

import java.util.List;

public interface TipOffDynamicMapper {

    // 插入举报
    void insertTipOff(TipOffDynamic tipOffDynamic);

    // 修改举报数据
    void updateTipOff(TipOffDynamic tipOffDynamic);

    // 通过被举报的动态id获取举报数据
    List<TipOffDynamic> selectTipOffByDynamicId(int dynamicId);
}
