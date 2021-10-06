package com.HISM.backfront.Service;

import com.HISM.backfront.domain.App;
import com.HISM.backfront.mapper.AppMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AppSerive {
    @Resource
    private AppMapper appMapper;

    // 添加App
    public Boolean insertApp(App app){
        try{
            appMapper.insertApp(app);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    // 删除App
    public Boolean deleteApp(int appId){
        try{
            appMapper.deleteApp(appId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    // 更新App
    public Boolean updateApp(App app){
        try{
            appMapper.updateApp(app);
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    // 查找App
    public App selectApp(int appId){
        App app = null;
        try{
           app = appMapper.selectApp(appId);

        }catch (Exception e){
            e.printStackTrace();
        }
        return app;
    }
}
