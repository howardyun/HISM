package com.HISM.backfront.Service;

import com.HISM.backfront.domain.Administrator;
import com.HISM.backfront.mapper.AdministratorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdministratorSerive {

    @Resource
    AdministratorMapper administratorMapper;

    /**
     * 通过Id查询管理员账户
     * @param AdminId
     * @return com.HISM.backfront.domain.Administrator
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2021/12/3 10:34 下午
     */
    public Administrator queryUserbyId(String AdminId){
        Administrator administrator = administratorMapper.queryAdministratorById(AdminId);
        if(administrator == null) {
            System.out.println("该用户id不存在");
            return null;
        }
        return administrator;
    }
}
