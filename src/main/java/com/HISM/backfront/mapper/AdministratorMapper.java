package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.Administrator;

public interface AdministratorMapper {
    // 通过Id查询管理员
    Administrator queryAdministratorById(String AdminId);

}
