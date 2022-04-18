package com.HISM.backfront.Controller;

import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;


@RestController
//必填
@Api(tags = "管理员操作接口")
@RequestMapping("/admin")
@CrossOrigin("http://39.106.25.203")
public class AdminController {

    @Resource
    UserService userService;
    @Resource
    AdministratorSerive administratorSerive;
    @Resource
    DynamicSerive dynamicSerive;

    public boolean verifyId(String Id, MyResult myResult) {
        //        判空
        if ("".equals(Id)) {
            myResult.changeStatus(false);
            myResult.add("message", "id不能为空");
            return false;
        } else {
            return true;
        }
    }

    @PostMapping("/getData")
    @ApiOperation("获取数据")
    public MyResult getData(@RequestParam String adminId) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else {
            //获取用户数量
            List<User> userNumber = userService.selectUserAll();
            //获取被举报够五次用户数量
            List<User> userReportedNumber = userService.selectUserByState(0);
            //获取当前动态数量
            List<Dynamic> momentNumber = dynamicSerive.selectDynamicAll();
            //获取被举报够五次动态数量
            List<Dynamic> momentReportedNumber = dynamicSerive.selectDynamicByState(0);
            myResult.changeStatus(true);
            Map<String, Object> map = new HashMap<>(4);
            map.put("userNumber", userNumber.size());
            map.put("userReportedNumber", userReportedNumber.size());
            map.put("momentNumber", momentNumber.size());
            map.put("momentReportedNumber", momentReportedNumber.size());
            myResult.add("message", map);
        }

        return myResult;
    }

    @PostMapping("/userMoments")
    @ApiOperation("获取某用户动态")
    public MyResult userMoments(@RequestParam String adminId, @RequestParam String userId) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);

        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else {
            myResult.changeStatus(true);
            List<Dynamic> dynamics = dynamicSerive.selectDynamicByUserId(userId);
            List<User> user = userService.selectUserbyId(userId);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (Dynamic dynamic : dynamics) {
                Map<String, Object> map = new HashMap<>();
                map.put("momentID", dynamic.getDynamicId());
                map.put("userID", dynamic.getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatar", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());
                map.put("appendixType", dynamic.getDynamicType());
                if (dynamic.getDynamicType().equals("0")) {
                    map.put("text", dynamic.getDynamicContent());
                } else if (dynamic.getDynamicType().equals("1")) {
                    if (dynamic.getDynamicContent().contains(";")) {
                        String[] urls = dynamic.getDynamicContent().split(";");
                        for (int i = 0; i < urls.length; i++) {
                            String url = urls[i];
                            map.put("photos" + (i + 1), url);
                        }
                    } else {
                        map.put("photo", dynamic.getDynamicContent());
                    }
                } else if (dynamic.getDynamicType().equals("2")) {
                    map.put("video", dynamic.getDynamicContent());
                } else if (dynamic.getDynamicType().equals("3")) {
                    map.put("program", dynamic.getDynamicContent());
                }
                map.put("likeNum", dynamic.getThumbNum());
                map.put("commentNum", dynamic.getCommentNum());
                map.put("tag", dynamic.getDynamicIndex());
                tmp.add(map);
            }
            myResult.add("message", tmp);
        }
        return myResult;
    }
}
