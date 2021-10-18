package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.Thumb;
import com.HISM.backfront.mapper.DynamicMapper;
import com.HISM.backfront.mapper.ThumbMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ThumbSerive {
    @Resource
    ThumbMapper thumbMapper;

    @Resource
    DynamicMapper dynamicMapper;

    // 插入点赞
    public boolean insertThumb(Thumb thumb){
        try{
            thumbMapper.insertThumb(thumb);
            Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(thumb.getDynamicId());
            dynamic.setThumbNum(dynamic.getThumbNum() + 1);
            dynamicMapper.updateDynamic(dynamic);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 删除点赞
    public boolean deleteThumb(int dynamicId, String userId){
        try{
            thumbMapper.deleteThumb(dynamicId, userId);
            Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(dynamicId);
            dynamic.setThumbNum(dynamic.getThumbNum() - 1);
            dynamicMapper.updateDynamic(dynamic);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // 判断某个用户是否对某个动态进行点赞
    public Boolean isThumb(String userId, int dynamicId){
        int thumbNum = thumbMapper.isThumb(dynamicId, userId);
        // 如果点赞数量=1 则表名用户对该动态点了赞，返回true 否则返回false
        return thumbNum == 1;
    }
}
