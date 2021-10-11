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
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public MyResult createMomentWithPhotos(@RequestParam String userId,@RequestParam("editormd-image-file") MultipartFile []multipartFile,@RequestParam String text,@RequestParam String tag) {
        MyResult myResult=new MyResult();
        if("".equals(userId)||"".equals(text)||"".equals(tag)||multipartFile==null){
            myResult.changeStatus(false);
            myResult.add("message","userId或text或tag为空");
            return myResult;
        }
        List<User> users=userService.queryUserbyId(userId);
        if(users==null){
            myResult.changeStatus(false);
            myResult.add("message","无该用户信息");
        }else if(users.size()>1){
            myResult.changeStatus(false);
            myResult.add("message","用户信息");
        }else{

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
        MyResult myResult=new MyResult();

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

        List<User> users = userService.queryUserbyId(userId);
        if(users==null){
            myResult.changeStatus(false);
            myResult.add("message", "无该用户");
        }else if (users.size()>1){
            myResult.changeStatus(false);
            myResult.add("message", "用户id大于1个");
        }else {
            Dynamic dynamic=new Dynamic();
            //初始设置为0
            dynamic.setCommentNum(0);
            //公开
            dynamic.setDynamicAccess(1);
            //设置内容
            dynamic.setDynamicContent(text);
            //设置点赞数量
            dynamic.setThumbNum(0);
            //设置类型
            dynamic.setDynamicType("Text only");
            //设置时间
            Date date = new Date(System.currentTimeMillis());
            Timestamp timeStamep = new Timestamp(date.getTime());
            dynamic.setDynamicTime(timeStamep);
            //设置标签
            dynamic.setDynamicIndex1(tag);
            dynamicSerive.insertDynamic(dynamic);
            myResult.changeStatus(true);
            myResult.add("message","");
        }


        return myResult;
    }

    @PostMapping("/likeComment")
    //必填
    @ApiOperation("点赞评论")
    public String likeComment(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {
        return userId;
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
    public String getUsersMoments(@RequestParam String userId, @RequestParam String text, @RequestParam String tag) {





        return userId;
    }


    @PostMapping("/getMoments")
    //必填
    @ApiOperation("获取动态")

    public MyResult getMoments(){
        MyResult myResult=new MyResult();

        return myResult;
    }



}
