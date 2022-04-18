package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Administrator;

public interface AdministratorMapper {
    /**
    *
    * @param AdminId
    * @return com.HISM.backfront.domain.Administrator
    * @author ysx
    * @creed: Talk is cheap,show me the code
    * @date 2022/4/18 5:02 下午
    */
    Administrator queryAdministratorById(String AdminId);

}
