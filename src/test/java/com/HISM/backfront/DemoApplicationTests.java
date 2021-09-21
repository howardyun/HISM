package com.HISM.backfront;

import com.HISM.backfront.domain.User;
import com.HISM.backfront.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
    }


    @Test
    void insertUserTest(){
        try{
            User user=new User("16","123","123","123",12,"man","test,test");
            userMapper.insertUser(user);
        }catch (Exception e){
            System.out.println("该用户已注册");
        }
    }

    @Test
    void updateUserTest(){
        try{
            User user=new User("99","123","bbc","123",19,"man","test,test");
            userMapper.updateUser(user);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("该用户不存在,无法更新数据");
        }
    }

    @Test
    void queryUserbyNameTest(){
        List<User> userList = userMapper.queryUserbyName("bbc");
        if(userList.isEmpty()){
            System.out.println("该用户不存在");
        }else{
            userList.forEach(System.out::println);
        }
    }

    @Test
    void queryUserbyIdTest(){
        List<User> userList = userMapper.queryUserbyId("16");
        if(userList.isEmpty()){
            System.out.println("该用户不存在");
        }else{
            User user = userList.get(0);
            System.out.println(user.toString());
        }
    }

}
