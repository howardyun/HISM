package com.HISM.backfront.Controller;

import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.*;
import com.HISM.backfront.domain.Administrator;
import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @Resource
    TipOffDynamicSerive tipOffDynamicSerive;
    @Resource
    ThumbSerive thumbSerive;
    @Resource
    TipOffUserSerive tipOffUserSerive;

    public boolean verifyId(String Id, MyResult myResult) {
        if ("".equals(Id)) {
            myResult.changeStatus(false);
            myResult.add("message", "id不能为空");
            return false;
        }
//        //判断id合法性(合法id构成：第一个字符为字母，Id中无特殊符号，只能是字母和数字的组合)
//        if (Id.charAt(0) == '0' || Id.charAt(0) == '1' || Id.charAt(0) == '2' || Id.charAt(0) == '3' || Id.charAt(0) == '4' ||
//                Id.charAt(0) == '5' || Id.charAt(0) == '6' || Id.charAt(0) == '7' || Id.charAt(0) == '8' || Id.charAt(0) == '9') {
//            myResult.changeStatus(false);
//            myResult.add("message", "id中首字符为数字");
//            return false;
//        }
//        Pattern idComposition = Pattern.compile("[a-zA-Z0-9]*");
//        Matcher idMatcher = idComposition.matcher(Id);
//        if (!idMatcher.matches()) {
//            myResult.changeStatus(false);
//            myResult.add("message", "id中有非法字符,只能是字母和数字组合");
//            return false;
//        }
        return true;
    }

    @PostMapping("/adminLogin")
    @ApiOperation("管理员登陆")
    public MyResult adminLogin(@RequestParam String adminId, @RequestParam String adminPassword) {
        MyResult myResult = new MyResult();
        if ("".equals(adminId) || "".equals(adminPassword)) {
            myResult.changeStatus(false);
            myResult.add("message", "userId或为空");
            return myResult;
        }

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else {

            if (adminPassword.equals(administrator.getPassword())) {
                Map<String, Object> map = new HashMap<>();
                map.put("adminId", adminId);
                myResult.changeStatus(true);
                myResult.add("message", map);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "密码错误");
            }
        }
        return myResult;
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
            List<User> userNumber = userService.selectUserAll();
            List<User> userReportedNumber = userService.selectUserByState(0);
            List<Dynamic> momentNumber = dynamicSerive.selectDynamicAll();
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


    @PostMapping("/reportedUser")
    @ApiOperation("获取所有被封用户基本信息")  //获取所有处于 userStatus 状态的用户
    public MyResult reportedUser(@RequestParam String adminId, @RequestParam int userStatus) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else {
            myResult.changeStatus(true);
            List<User> RequestedUsers = userService.selectUserByState(userStatus);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (User user : RequestedUsers) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("userID", user.getUserId());
                map.put("userName", user.getUserName());
                map.put("userTimes", user.getTipOffNum());
                map.put("userStatus", user.getUserState());
                tmp.add(map);
            }
            myResult.add("message", tmp);
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

    @PostMapping("/reportedMoments")
    @ApiOperation("获取被封动态")
    public MyResult reportedMoments(@RequestParam String adminId, @RequestParam int dynamicState) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else if (dynamicState != 0) {
            myResult.changeStatus(false);
            myResult.add("message", "该动态未被封");
        } else {
            myResult.changeStatus(true);
            List<Dynamic> dynamics = dynamicSerive.selectDynamicByState(dynamicState);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (Dynamic dynamic : dynamics) {
                Map<String, Object> map = new HashMap<>();
                List<User> user = userService.selectUserbyId(dynamic.getUserId());
                map.put("tag", dynamic.getDynamicIndex());
                map.put("momentID", dynamic.getDynamicId());
                map.put("userID", dynamic.getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatar", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());
                map.put("likedNum", dynamic.getThumbNum());
                map.put("commentNum", dynamic.getCommentNum());
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
                tmp.add(map);
            }
            myResult.add("message", tmp);
        }
        return myResult;
    }


    @PostMapping("/aduitMoments")
    @ApiOperation("审核被封动态")
    public MyResult aduitMoments(@RequestParam String adminId, @RequestParam int dynamicId, @RequestParam int dynamicState) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
            return myResult;
        }
        Dynamic dynamic = dynamicSerive.selectDynamicByDynamicId(dynamicId);
        if (dynamic == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该动态不存在");
            return myResult;
        } else {
            int i = dynamic.getDynamicState();
            if (i == -1) {
                myResult.changeStatus(false);
                myResult.add("message", "该动态已永久被封");
            } else if (i == 0) {
                if (dynamicState == 0) {
                    dynamic.setDynamicState(-1);
                } else {
                    dynamic.setDynamicState(2);
                }
                dynamicSerive.updateDynamic(dynamic);
                tipOffDynamicSerive.invalidateTipOff(dynamicId);
                myResult.changeStatus(true);
                Map<String, Object> map = new HashMap<>(1);
                map.put("status", dynamicState);
                myResult.add("message", map);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "该动态没有被封");
            }
        }
        return myResult;

    }


    @PostMapping("/selectUserAllDynamics")
    @ApiOperation("搜索用户所有")
    public MyResult selectUserAllDynamics(@RequestParam String adminId, @RequestParam String userId) {
        MyResult myResult = new MyResult();
        Administrator administrator = administratorSerive.queryUserbyId(adminId);

        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else {
            List<User> user = userService.selectUserbyId(userId);
            if (user == null) {
                myResult.changeStatus(false);
                myResult.add("message", "该管理员不存在");
                return myResult;
            } else {
                List<Dynamic> dynamicList = dynamicSerive.selectDynamicByUserId(userId);
                myResult.changeStatus(true);
                List<Map<String, Object>> tmp = new ArrayList<>();
                for (int i = 0; i < dynamicList.size(); i++) {
                    Map<String, Object> map = new HashMap<>(4);
                    map.put("momentId", dynamicList.get(i).getDynamicId());
                    map.put("userID", user.get(0).getUserId());
                    map.put("userName", user.get(0).getUserName());
                    map.put("userAvatar", user.get(0).getAvatarURL());
                    map.put("time", dynamicList.get(i).getDynamicTime());
                    map.put("text", dynamicList.get(i).getText());
                    map.put("likedNum", dynamicList.get(i).getThumbNum());
                    map.put("commentNum", dynamicList.get(i).getCommentNum());
                    map.put("isLiked", thumbSerive.isThumb(userId, dynamicList.get(i).getDynamicId()));
                    map.put("isDel", dynamicList.get(i).getDynamicState() == 3);
                    map.put("tag", dynamicList.get(i).getDynamicType());
                    String dynamicType = dynamicList.get(i).getDynamicType();
                    map.put("appendixType", dynamicList.get(i).getDynamicType());
                    if (dynamicType.equals("1")) {
                        int imageLength = dynamicList.get(i).getDynamicContent().split(";").length;
                        if (imageLength == 0) {
                            myResult.changeStatus(false);
                            myResult.add("message", "动态中不包含图片");
                            return myResult;
                        } else {
                            map.put("photos", dynamicList.get(i).getDynamicContent());
                        }
                    } else if (dynamicType.equals("2")) {
                        map.put("video", dynamicList.get(i).getDynamicContent());
                    } else if (dynamicType.equals("3") || dynamicType.equals("4")) {
                        map.put("momentId", dynamicList.get(i).getDynamicId());
                    } else if (dynamicType.equals("0")) {

                    } else {
                        myResult.changeStatus(false);
                        myResult.add("message", "动态类型码错误");
                        return myResult;
                    }
                    tmp.add(map);
                }
                myResult.add("message", tmp);

            }
        }


        return myResult;
    }

    @PostMapping("/aduitUser")
    @ApiOperation("审核被封动态")
    public MyResult aduitUser(){
        MyResult myResult = new MyResult();


        return myResult;
    }


}
