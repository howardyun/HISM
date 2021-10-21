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

    // 通过动态发布者的id查找动态
    public List<Dynamic> selectDynamicByUserId(String userId);

    // 通过动态发布者的id和动态的状态查找动态
    public List<Dynamic> selectDynamicByUserIdAndState(String userId, int dynamicState);

    // 通过动态发布者的id和动态的任意（or）两个状态查找动态
    public List<Dynamic> selectDynamicByUserIdAnd2State(String userId, int dynamicState1, int dynamicState2);

    // 通过举报次数查询动态
    public List<Dynamic> selectDynamicByTipOffNum(int tipOffNum);

    // 获取所有动态
    public List<Dynamic> selectDynamicAll();

    // 获取所有指定状态的动态
    public List<Dynamic> selectDynamicByState(int dynamicState);

    // 根据动态标签获取动态
    public List<Dynamic> selectDynamicByIndex(String index);

    // 根据动态类型查看动态
    public List<Dynamic> selectDynamicByType(int appState);

    // 获取该用户某一动态后的num条动态
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdLimitNUM(String userId, int dynamicId, int num);

    // 获取该某一动态后的num条动态
    public List<Dynamic> selectDynamicByDynamicIdLimitNUM(int dynamicId, int num);

    // 获取该用户某一动态后的20条为的动态，要求20调动态的标签为dynamicIndex
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdAndDynamicIndexLimitNUM(String userId, int dynamicId, String dynamicIndex, int num);


    // 获取该用户某一动态后num条动态 要求20条动态的状态为dynamicState
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdAndDynamicStateLimitNUM(String userId, int dynamicId, int dynamicState, int num);
}
