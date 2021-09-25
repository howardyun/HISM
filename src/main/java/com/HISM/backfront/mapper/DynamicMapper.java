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

    // 查看动态
    public List<Dynamic> selectDynamicByUserName(String userName);

    // 更新动态
    public void updateDynamic(Dynamic dynamic);

    // 通过动态id
    public Dynamic selectDynamicByDynamicId(int DynamicId);

}
