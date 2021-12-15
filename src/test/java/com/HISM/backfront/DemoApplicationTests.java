package com.HISM.backfront;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

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

    Date date = new Date();
    Comment comment = new Comment(date, "你要不要吧", 311, "9999");


    @Test
    void Test(){
//        Thumb thumb = new Thumb(366, "9999");
//        thumbSerive.deleteThumb(366, "9999");

        thumbSerive.selectThumbInfoByDynamicId(389);

        for(Thumb thumb : thumbSerive.selectThumbInfoByDynamicId(389)){
            System.out.println(thumb.toString());
        }















//        List<Dynamic> dynamicList = dynamicSerive.selectDynamicByDynamicIdLimitNUM(1, 5);
//        for(Dynamic dynamic : dynamicList){
//            System.out.println(dynamic.getDynamicTime());
//        }



//        System.out.println(dynamicSerive.selectDynamicByIndex("鬼畜").get(1)+ "---------");
        // Dynamic dynamic = new Dynamic("小程序", 2,"金轮秀肌肉小程序", date, "程序", "123", "python", "空代码", "wu", "sdf", "fsd", "baidu.com", 1);
        // System.out.println(dynamicSerive.selectDynamicByUserIdAndDynamicIdLimit20("16888", 322, 13).size());





    }

    @Test
    void insertUser(){
        User user=new User("testtest","1","tytyty","dfs",12,"male","测试",1,null,"howard.yun@outlook.com");
        userService.insertUser(user);
    }

}
