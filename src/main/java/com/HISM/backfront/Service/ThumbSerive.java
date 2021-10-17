package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Thumb;
import com.HISM.backfront.mapper.ThumbMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class ThumbSerive {
    @Resource
    ThumbMapper thumbMapper;

    // 插入点赞
    public boolean insertThumb(Thumb thumb){
        int thumbNum = thumbMapper.isThumb(thumb.getDynamicId(), thumb.getUserId());
        if(thumbNum==0){
            thumbMapper.insertThumb(thumb);
            return true;
        }
        System.out.println("该条动态您已点赞");

        return false;
//        try{
//            thumbMapper.insertThumb(thumb);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }

    }

    // 删除点赞
    public boolean deleteThumb(int dynamicId, String userId){
        int thumbNum = thumbMapper.isThumb(dynamicId, userId);
        if(thumbNum==1){
            thumbMapper.deleteThumb(dynamicId, userId);
            return true;
        }
        System.out.println("该动态未点赞");
        return false;
//        try{
//            thumbMapper.deleteThumb(dynamicId, userId);
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//            return false;
//        }
    }

    // 判断某个用户是否对某个动态进行点赞
    public Boolean isThumb(String userId, int dynamicId){
        int thumbNum = thumbMapper.isThumb(dynamicId, userId);
        if(thumbNum == 1){
            return true;
        }
        return false;
    }
}
