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

    /**
     * 通过动态id插入一次举报记录， 举报次数加一，判断是否需要封锁动态
     *
     * @param tipOffDynamic
     * @return
     */
    public int insertTipOff(TipOffDynamic tipOffDynamic) {
        Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(tipOffDynamic.getDynamicId());

        try {
            //插入一次举报记录
            tipOffDynamicMapper.insertTipOff(tipOffDynamic);
        } catch (Exception e) {
            System.out.println("用户重复举报动态!!");
            return 222;
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


    /**
     * 通过被举报的动态id获取举报数据
     *
     * @param dynamicId
     * @return
     */
    public List<TipOffDynamic> selectTipOffByDynamicId(int dynamicId) {
        List<TipOffDynamic> tipOffDynamicList = tipOffDynamicMapper.selectTipOffByDynamicId(dynamicId);
        if (tipOffDynamicList.isEmpty()) {
            System.out.println("该动态无举报记录");
        }
        return tipOffDynamicList;
    }


    /**
     * 解封动态，设置dynamicState为2， 使该动态的所有举报无效
     *
     * @param dynamicId
     * @return java.lang.Boolean
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:54 下午
     */
    public Boolean invalidateTipOff(int dynamicId) {
        // 解封动态，设置dynamicState为2
        Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(dynamicId);
        dynamic.setDynamicState(2);
        dynamicMapper.updateDynamic(dynamic);

        //  设置为0 使该动态所有相关举报无效
        List<TipOffDynamic> tipOffDynamicList = selectTipOffByDynamicId(dynamicId);
        try {
            for (int i = 0; i < tipOffDynamicList.size(); i++) {
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
