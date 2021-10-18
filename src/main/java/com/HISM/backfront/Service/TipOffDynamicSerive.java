package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.TipOffDynamic;
import com.HISM.backfront.mapper.DynamicMapper;
import com.HISM.backfront.mapper.TipOffDynamicMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class TipOffDynamicSerive extends FollowerSerive {
    @Resource
    TipOffDynamicMapper tipOffDynamicMapper;

    @Resource
    DynamicMapper dynamicMapper;

    // 功能：通过动态id插入一次举报记录， 举报次数加一，判断是否需要封锁动态
    public int insertTipOff(TipOffDynamic tipOffDynamic) {
        Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(tipOffDynamic.getDynamicId());

        try{
            //插入一次举报记录
            tipOffDynamicMapper.insertTipOff(tipOffDynamic);
        }catch (Exception e){
            System.out.println("用户重复举报动态!!");
            return 2;
        }
        dynamic.setTipOffNum(dynamic.getTipOffNum() + 1);

        // 举报次数 >= 5 动态暂时被封；
        if (dynamic.getTipOffNum() >= 5) {
            dynamic.setDynamicState(0);
        }
        // 更新动态
        dynamicMapper.updateDynamic(dynamic);

        return dynamic.getDynamicState();
    }

    // 通过被举报的动态id获取举报数据
    public List<TipOffDynamic> selectTipOffByDynamicId(int dynamicId) {
        List<TipOffDynamic> tipOffDynamicList = tipOffDynamicMapper.selectTipOffByDynamicId(dynamicId);
        if (tipOffDynamicList.isEmpty()) {
            System.out.println("该动态无举报记录");
        }
        return tipOffDynamicList;
    }

    // 使某一动态的举报无效  动态解封后使用
    public Boolean invalidateTipOff(int dynamicId) {
        List<TipOffDynamic> tipOffDynamicList = selectTipOffByDynamicId(dynamicId);
        try{
            for(int i = 0; i < tipOffDynamicList.size(); i++) {
                // 设置为0 使动态无效
                tipOffDynamicList.get(i).setIsValid(0);
                tipOffDynamicMapper.updateTipOff(tipOffDynamicList.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
