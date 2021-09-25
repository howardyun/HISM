package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Thumb;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ThumbMapper {
    // 插入点赞
    public void insertThumb(Thumb thumb);

    // 删除点赞
    public void deleteThumb(int dynamicId, String userId);

}
