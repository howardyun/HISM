package com.HISM.backfront;

import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.*;
import com.HISM.backfront.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static java.sql.JDBCType.NULL;

@SpringBootTest
class DemoApplicationTests {



    @Autowired
    DynamicSerive dynamicSerive;

    @Autowired
    ThumbSerive thumbSerive;

    @Autowired
    FollowerSerive followerSerive;


    @Test
    void contextLoads() {
    }


    @Test
    void Test(){
        Date date = new Date(System.currentTimeMillis());
        Comment comment = new Comment(date, "What's up，你这瓜皮子是金子做的, 还是瓜粒子是金子做的？", 311, "16");
        Dynamic dynamic = new Dynamic("鬼畜", "全明星", 1, "大家都来说C语言", date, 666, 46, 0, "1", "fox");
        // Chat chat = new Chat("123", "16", new Date(System.currentTimeMillis()), "blbl");
        // userService.updateUser(user);
        // System.out.println(chatSerive.queryChatRecording("123", "16"));
        // dynamicSerive.insertDynamic(dynamic);
        // dynamicSerive.deleteDynamic(312);

//        dynamic = dynamicSerive.selectDynamicByDynamicId(313);
//        System.out.println(dynamic.toString());
//        dynamic.setCommentNum(99);
//        dynamicSerive.updateDynamic(dynamic);

        // thumbSerive.deleteThumb(313, "16");

        Follower follower = new Follower("123", "16", date);
        followerSerive.deleteFollower("123", "16");

    }
}
