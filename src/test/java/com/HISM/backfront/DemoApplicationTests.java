package com.HISM.backfront;

import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void testMybatis(){
        User user=new User("123456","123","123","123",12,"man","test,test");

        userMapper.insertUser(user);

    }

}
