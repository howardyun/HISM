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
        try{
            thumbMapper.insertThumb(thumb);
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
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
