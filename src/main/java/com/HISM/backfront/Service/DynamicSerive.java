package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.mapper.DynamicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DynamicSerive {
    @Resource
    DynamicMapper dynamicMapper;
    // 添加动态
    public boolean insertDynamic(Dynamic dynamic){
        try{
            dynamicMapper.insertDynamic(dynamic);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 删除动态
    public boolean deleteDynamic(int dynamicId){
        try{
            dynamicMapper.deleteDynamic(dynamicId);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // 通过发送者id查看动态
    public List<Dynamic> selectDynamicByUserId(String userId){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserId(userId);
        if(dynamicList.isEmpty()){
            System.out.println("error, 无法查到该用户的动态");
        }
        return dynamicList;
    }

    // 通过动态发布者的id和动态的一个状态 获取动态
    public List<Dynamic> selectDynamicByUserIdAndState(String userId, int dynamicState){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserIdAndState(userId, dynamicState);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }
        return dynamicList;
    }


    // 通过动态发布者的id和动态的任意（or）两个状态查找动态
    public List<Dynamic> selectDynamicByUserIdAnd2State(String userId, int dynamicState1, int dynamicState2){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserIdAnd2State(userId, dynamicState1, dynamicState2);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }
        return dynamicList;
    }

    // 通过动态Id查看动态
    public Dynamic selectDynamicByDynamicId(int DynamicId){
        try{
            Dynamic dynamic = dynamicMapper.selectDynamicByDynamicId(DynamicId);
            return dynamic;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error, 无法查到此动态");
            return null;
        }
    }

    // 更新动态
    public boolean updateDynamic(Dynamic dynamic){
        try{
            dynamicMapper.updateDynamic(dynamic);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


    // 通过举报次数查询动态
    public List<Dynamic> selectDynamicByTipOffNum(int tipOffNum){
        if(0 > tipOffNum){
            System.out.println("err, 被举报次数为负数");
            return null;
        }
        return dynamicMapper.selectDynamicByTipOffNum(tipOffNum);
    }

    // 获取所有动态
    public List<Dynamic> selectDynamicAll(){
        return dynamicMapper.selectDynamicAll();
    }

    // 通过动态的状态，获取动态。
    public List<Dynamic> selectDynamicByState(int dynamicState){
        return dynamicMapper.selectDynamicByState(dynamicState);
    }

    // 通过动态标签获取动态
    public List<Dynamic> selectDynamicByIndex(List<String> indexList){
        List<Dynamic> dynamicList = new ArrayList<>();
        for(String index: indexList){
            dynamicList.addAll(dynamicMapper.selectDynamicByIndex(index)) ;
        }
        return dynamicList;
    }

    // 根据动态类型查看动态
    public List<Dynamic> selectDynamicByType(int appState){
        return dynamicMapper.selectDynamicByType(appState);
    }


    // 获取该用户某一动态后的20条动态
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdLimit20(String userId, int dynamicId, int num){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserIdAndDynamicIdLimitNUM(userId, dynamicId, num);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }else if(dynamicList.size() < num){
            System.out.println("获取的动态数量不满足"+num+"条");
        }
        return dynamicList;
    }


    // 获取该用户某一动态后的num条动态 要求num条动态的状态为dynamicState
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdAndDynamicStateLimitNUM(String userId, int dynamicId, int dynamicState, int num){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserIdAndDynamicIdAndDynamicStateLimitNUM(userId, dynamicId, dynamicState, num);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }else if(dynamicList.size() < num){
            System.out.println("获取的动态数量不满足"+num+"条");
        }
        return dynamicList;
    }


    // 获取该用户某一动态后的num条为的动态，要求num条动态的标签为dynamicIndex
    public List<Dynamic> selectDynamicByUserIdAndDynamicIdAndDynamicIndexLimitNUM(String userId, int dynamicId, String dynamicIndex, int num){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserIdAndDynamicIdAndDynamicIndexLimitNUM(userId, dynamicId, dynamicIndex, num);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }else if(dynamicList.size() < num){
            System.out.println("获取的动态数量不满足"+num+"条");
        }
        return dynamicList;
    }

    // 获取该某一动态后的num条动态
    public List<Dynamic> selectDynamicByDynamicIdLimitNUM(int dynamicId, int num){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByDynamicIdLimitNUM(dynamicId, num);
        if(dynamicList.isEmpty()){
            System.out.println("error, 符合该条件的动态数量为0");
        }else if(dynamicList.size() < num){
            System.out.println("获取的动态数量不满足"+num+"条");
        }
        return dynamicList;
    }
}
