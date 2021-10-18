package com.HISM.backfront;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.TipOffDynamic;
import com.HISM.backfront.domain.TipOffUser;
import com.HISM.backfront.domain.User;
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
        Date date = new Date();
//        System.out.println(dynamicSerive.selectDynamicByIndex("鬼畜").get(1)+ "---------");
        Dynamic dynamic = new Dynamic("小程序", 2,"金轮秀肌肉小程序", date, "程序", "123", "python", "空代码", "wu", "sdf", "fsd", "baidu.com", 1);
        dynamicSerive.insertDynamic(dynamic);
    }

    @Test
    void Testresult(){

        WebAppConfig webAppConfig =new WebAppConfig();
        System.out.print(webAppConfig.location);
    }

}
