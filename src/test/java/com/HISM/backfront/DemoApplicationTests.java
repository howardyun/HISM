package com.HISM.backfront;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.TipOffDynamic;
import com.HISM.backfront.domain.TipOffUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

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

    @Autowired
    TipOffDynamicSerive tipOffDynamicSerive;

    @Autowired
    TipOffUserSerive tipOffUserSerive;

    @Autowired
    CommentSerive commentSerive;


    @Test
    void contextLoads() {
    }


    @Test
    void Test(){
//        System.out.println(dynamicSerive.selectDynamicByIndex("鬼畜").get(1)+ "---------");
        System.out.println(commentSerive.isComment("16", 99));
    }

    @Test
    void Testresult(){

        WebAppConfig webAppConfig =new WebAppConfig();
        System.out.print(webAppConfig.location);
    }

}
