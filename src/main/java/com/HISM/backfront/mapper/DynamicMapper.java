package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Dynamic;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicMapper {
    // 添加动态
    public void insertDynamic(Dynamic dynamic);

    // 删除动态
    public void deleteDynamic(int dynamicId);

    // 更新动态
    public void updateDynamic(Dynamic dynamic);

    // 通过动态id查找动态
    public Dynamic selectDynamicByDynamicId(int DynamicId);

    // 通过动态发布者的姓名查找动态
    public List<Dynamic> selectDynamicByUserId(String userId);


}
