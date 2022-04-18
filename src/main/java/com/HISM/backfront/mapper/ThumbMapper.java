package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Thumb;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ThumbMapper {

    /**
     * 插入点赞
     * @param thumb
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:32 下午
     */
    public void insertThumb(Thumb thumb);

    /**
     * 删除点赞
     * @param dynamicId
	 * @param userId
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:32 下午
     */
    public void deleteThumb(int dynamicId, String userId);

    /**
     * 判断某个用户是否对某个动态进行点赞
     * @param dynamicId
	 * @param userId
     * @return int
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:33 下午
     */
    public int isThumb(int dynamicId, String userId);
    /**
     * 通过动态的ID获取所有的点赞信息
     * @param dynamic
     * @return java.util.List<com.HISM.backfront.domain.Thumb>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:33 下午
     */
    public List<Thumb> selectThumbInfoByDynamicId(int dynamic);

}
