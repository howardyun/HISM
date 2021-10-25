package com.HISM.backfront;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.*;
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
        Comment comment = new Comment(date, "不气盛能叫年轻人吗", 311, "9999");
        Thumb thumb = new Thumb(347, "9999");









//        System.out.println(dynamicSerive.selectDynamicByIndex("鬼畜").get(1)+ "---------");
        // Dynamic dynamic = new Dynamic("小程序", 2,"金轮秀肌肉小程序", date, "程序", "123", "python", "空代码", "wu", "sdf", "fsd", "baidu.com", 1);
        // System.out.println(dynamicSerive.selectDynamicByUserIdAndDynamicIdLimit20("16888", 322, 13).size());

        commentSerive.insertComment(comment);



    }

    @Test
    void Testresult(){

        WebAppConfig webAppConfig =new WebAppConfig();
        System.out.print(webAppConfig.location);
    }

}
