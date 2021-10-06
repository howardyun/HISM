package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.App;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AppMapper {

    // 添加App
    void insertApp(App app);
    // 删除App
    void deleteApp(int appId);
    // 更新App
    void updateApp(App app);
    // 查找App
    App selectApp(int appId);

}
