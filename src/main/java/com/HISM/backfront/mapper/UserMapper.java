package com.HISM.backfront.mapper;

import com.HISM.backfront.domain.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {

void insertUser(User user);
//void updateUser(User user);
//void deleteUser(User user);



}
