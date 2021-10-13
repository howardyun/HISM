package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Thumb;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ThumbMapper {
    // 插入点赞
    public void insertThumb(Thumb thumb);

    // 删除点赞
    public void deleteThumb(int dynamicId, String userId);

    // 判断某个用户是否对某个动态进行点赞
    public int isThumb(int dynamicId, String userId);

}
