package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.mapper.DynamicMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    // 通过发送者名称查看动态
    public List<Dynamic> selectDynamicByUserId(String userId){
        List<Dynamic> dynamicList = dynamicMapper.selectDynamicByUserId(userId);
        if(dynamicList.isEmpty()){
            System.out.println("error, 无法查到该用户的动态");
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

    public List<Dynamic> selectDynamicByState(int dynamicState){
        return dynamicMapper.selectDynamicByState(dynamicState);
    }

}
