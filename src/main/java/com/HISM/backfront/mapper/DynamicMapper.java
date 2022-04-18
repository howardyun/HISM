package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Dynamic;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface DynamicMapper {
    /**
     * 添加动态
     * @param dynamic
     */

    public void insertDynamic(Dynamic dynamic);


    /**
     * 删除动态
     * @param dynamicId
     * @return void
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:09 下午
     */
    public void deleteDynamic(int dynamicId);


    /**
     * 更新动态
     * @param dynamic
     */
    public void updateDynamic(Dynamic dynamic);

    /**
     * 通过动态id查找动态
     * @param DynamicId
     * @return
     */
    public Dynamic selectDynamicByDynamicId(int DynamicId);

    /**
     * 通过动态发布者的id查找动态
     * @param userId
     * @return
     */
    public List<Dynamic> selectDynamicByUserId(String userId);

    /**
     * 通过动态发布者的id和动态的状态查找动态
     * @param userId
     * @param dynamicState
     * @return
     */
    public List<Dynamic> selectDynamicByUserIdAndState(String userId, int dynamicState);

    /**
     * 通过动态发布者的id和动态的任意（or）两个状态查找动态
     * @param userId
     * @param dynamicState1
     * @param dynamicState2
     * @return
     */
    public List<Dynamic> selectDynamicByUserIdAnd2State(String userId, int dynamicState1, int dynamicState2);

    /**
     * 获取所有动态
     * @return
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:18 下午
     */
    public List<Dynamic> selectDynamicAll();

    /**
     * 获取所有指定状态的动态
     * @return
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:20 下午
     */
    public List<Dynamic> selectDynamicByState(int dynamicState);

    /**
     * 根据动态标签获取动态
     * @param index
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:20 下午
     */
    public List<Dynamic> selectDynamicByIndex(String index);

    /**
     * 根据动态类型查看动态
     * @param appState
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:21 下午
     */
    public List<Dynamic> selectDynamicByType(int appState);

    /**
     * 获取该用户某一动态后的num条动态
     * @param userId
	 * @param dynamicId
	 * @param num
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:22 下午
     */
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdLimitNUM(String userId, int dynamicId, int num);


    /**
     * 获取该某一动态后的num条动态
     * @param dynamicId
	 * @param num
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:22 下午
     */
    public List<Dynamic> selectDynamicByDynamicIdLimitNUM(int dynamicId, int num);

    /**
     * 获取该用户某一动态后num条动态 要求num条动态的状态为dynamicState
     * @param userId
	 * @param dynamicId
	 * @param dynamicState
	 * @param num
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:27 下午
     */
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdAndDynamicStateLimitNUM(String userId, int dynamicId, int dynamicState, int num);

    /**
     * 按照动态状态和动态类型选出动态，
     * @param dynamicState
	 * @param dynamicType
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:30 下午
     */
    public List<Dynamic> selectDynamicByDynamicStateAndDynamicType(int dynamicState, String dynamicType);

    /**
     * 获取num条动态，要求num条动态的状态为dynamicState、类型为DynamicType
     * @param dynamicId
	 * @param dynamicState
	 * @param dynamicType
	 * @param num
     * @return java.util.List<com.HISM.backfront.domain.Dynamic>
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/18 5:30 下午
     */
    public List<Dynamic> selectDynamicByDynamicIdAndDynamicStateAndDynamicTypeLimitNUM(int dynamicId, int dynamicState, String dynamicType, int num);
}


