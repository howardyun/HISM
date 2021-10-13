package com.HISM.backfront.Controller;

import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.DynamicSerive;
import com.HISM.backfront.Service.GeneralService;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//必填
@Api(tags = "动态管理接口")
@RequestMapping("/moment")
public class DynamicController {

    @Resource
    DynamicSerive dynamicSerive;
    @Resource
    UserService userService;
    @Resource
    GeneralService generalService;


    @PostMapping("/createMomentWithPhotos")
    //必填
    @ApiOperation("用户上传照片")
    public MyResult createMomentWithPhotos(@RequestParam String userId, @RequestParam("editormd-image-file") MultipartFile[] multipartFile, @RequestParam String text, @RequestParam String tag) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(text) || "".equals(tag) || multipartFile == null) {
            myResult.changeStatus(false);
            myResult.add("message", "userId或text或tag为空");
            return myResult;
        }
        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "无该用户信息");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户信息");
        } else {

        }
        return myResult;
    }

    @PostMapping("/createMomentWithVideo")
    //必填
    @ApiOperation("用户上传视频")
    public String createMomentWithVideo(@RequestParam String name) {
        return name;
    }

    @PostMapping("/createMomentWithCode")
    //必填
    @ApiOperation("用户上传代码")
    public MyResult createMomentWithCode(@RequestParam String userId, @RequestParam String text, @RequestParam String tag, @RequestParam String code) {
        MyResult myResult = new MyResult();

        return myResult;
    }

    @PostMapping("/createMomentOnlyText")
    //必填
    @ApiOperation("用户上传文本")
    public MyResult createMomentOnlyText(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {

        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(text) || "".equals(tag)) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id或text为空或tag为空");
            return myResult;
        }

        List<User> users = userService.selectUserbyId(userId);
        if (users == null) {
            myResult.changeStatus(false);
            myResult.add("message", "无该用户");
        } else if (users.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "用户id大于1个");
        } else {
            Dynamic dynamic = new Dynamic();
            //初始设置为0
            dynamic.setCommentNum(0);
            //公开
            dynamic.setDynamicState(1);
            //设置内容
            dynamic.setDynamicContent(text);
            //设置点赞数量
            dynamic.setThumbNum(0);
            //设置类型
            dynamic.setDynamicType("1");
            //设置时间
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamep = new Timestamp(date.getTime());
            dynamic.setDynamicTime(timeStamep);
            //设置标签
            dynamic.setDynamicIndex(tag);
            dynamicSerive.insertDynamic(dynamic);
            myResult.changeStatus(true);
            myResult.add("message", "");
        }


        return myResult;
    }

    @PostMapping("/likeComment")
    //必填
    @ApiOperation("点赞评论")
    public MyResult likeComment(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {
        MyResult myResult = new MyResult();


        return myResult;
    }

    @PostMapping("/delMoment")
    //必填
    @ApiOperation("删除动态")
    public String delMoment(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {


        return userId;
    }

    @PostMapping("/getUsersMoments")
    //必填
    @ApiOperation("获取用户动态")
    public MyResult getUsersMoments(@RequestParam String userId, @RequestParam String targetUserId, @RequestParam String lastMomentId, @RequestParam int length) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(targetUserId)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，目标用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        List<User> user1 = userService.selectUserbyId(targetUserId);

        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有源用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户信息多于一个");
        } else {
            if (user1 == null) {
                myResult.changeStatus(false);
                myResult.add("message", "没有目标用户");
            } else if (user1.size() > 1) {
                myResult.changeStatus(false);
                myResult.add("message", "目标用户多于一个");
            } else {
                List<Dynamic> dynamicList = dynamicSerive.selectDynamicByUserId(targetUserId);
                if (dynamicList.size() == 0) {
                    myResult.changeStatus(false);
                    myResult.add("message", "该用户没有动态");
                }
            }
        }


        return myResult;
    }


    @PostMapping("/getMoments")
    //必填
    @ApiOperation("获取动态")

    public MyResult getMoments() {
        MyResult myResult = new MyResult();

        return myResult;
    }

    @PostMapping("/getMomentByID")
    //必填
    @ApiOperation("获取单个动态")

    public MyResult getMomentByID(@RequestParam String userId, @RequestParam int momentId) {
        MyResult myResult = new MyResult();
        if ("".equals(userId) || "".equals(momentId)) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户id，目标用户id不能为空");
            return myResult;
        }
        List<User> user = userService.selectUserbyId(userId);
        if (user == null) {
            myResult.changeStatus(false);
            myResult.add("message", "没有源用户");
        } else if (user.size() > 1) {
            myResult.changeStatus(false);
            myResult.add("message", "源用户信息多于一个");
        } else {

            Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(momentId);
            if (dynamic == null) {
                myResult.changeStatus(false);
                myResult.add("message", "没有该动态");
            } else {

                Map<String, Object> map = new HashMap<>(4);
                map.put("momentId", momentId);
                map.put("userId", user.get(0).getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatar", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());

//                int dynamicType= dynamic.getDynamicType();
//                map.put("appendixType", dynamicstate);
//                if(d)

                map.put("likedNum", dynamic.getThumbNum());
                map.put("commentNum", dynamic.getCommentNum());
                map.put("isLiked", "");
                map.put("isDel", dynamic.getDynamicState() == 3);
                map.put("tag", dynamic.getDynamicType());


            }


        }
        return myResult;
    }

    @PostMapping("/likeMoment")
    //必填
    @ApiOperation("点赞/取消点赞")
    public MyResult likeMoment(){
        MyResult myResult=new MyResult();

        return myResult;
    }

    @PostMapping("/commentMoment")
    //必填
    @ApiOperation("发送评论")
    public MyResult commentMoment(){
        MyResult myResult=new MyResult();

        return myResult;
    }



}
