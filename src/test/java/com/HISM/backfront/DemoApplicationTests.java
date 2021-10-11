package com.HISM.backfront;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {



    @Autowired
    DynamicSerive dynamicSerive;

    @Autowired
    ThumbSerive thumbSerive;

    @Autowired
    FollowerSerive followerSerive;

    @Autowired
    AdministratorSerive administratorSerive;


    @Autowired
    UserService userService;

    @Autowired
    AppSerive appSerive;

    @Test
    void contextLoads() {
    }


    @Test
    void Test(){
        User user = userService.selectUserbyId("123").get(0);
        user.setTipOffNum(6);
        userService.updateUser(user);



    }

    @Test
    void Testresult(){

        WebAppConfig webAppConfig =new WebAppConfig();
        System.out.print(webAppConfig.location);
    }

}
