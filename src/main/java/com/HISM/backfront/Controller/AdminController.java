package com.HISM.backfront.Controller;

import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.AdministratorSerive;
import com.HISM.backfront.Service.DynamicSerive;
import com.HISM.backfront.Service.TipOffDynamicSerive;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.Administrator;
import com.HISM.backfront.domain.Dynamic;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
public class AdminController {

    @Resource
    UserService userService;
    @Resource
    AdministratorSerive administratorSerive;
    @Resource
    DynamicSerive dynamicSerive;
    @Resource
    TipOffDynamicSerive tipOffDynamicSerive;

    public boolean verifyId(String Id, MyResult myResult) {
        if ("".equals(Id)) {
            myResult.changeStatus(false);
            myResult.add("message", "id不能为空");
            return false;
        }
        //判断id合法性(合法id构成：第一个字符为字母，Id中无特殊符号，只能是字母和数字的组合)
        if (Id.charAt(0) == '0' || Id.charAt(0) == '1' || Id.charAt(0) == '2' || Id.charAt(0) == '3' || Id.charAt(0) == '4' ||
                Id.charAt(0) == '5' || Id.charAt(0) == '6' || Id.charAt(0) == '7' || Id.charAt(0) == '8' || Id.charAt(0) == '9') {
            myResult.changeStatus(false);
            myResult.add("message", "id中首字符为数字");
            return false;
        }
        Pattern idComposition = Pattern.compile("[a-zA-Z0-9]*");
        Matcher idMatcher = idComposition.matcher(Id);
        if (!idMatcher.matches()) {
            myResult.changeStatus(false);
            myResult.add("message", "id中有非法字符,只能是字母和数字组合");
            return false;
        }
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
            List<User> allUsers = userService.selectUserAll();
            List<User> blockedUsers = userService.selectUserByState(-1);
            List<Dynamic> allDynamics = dynamicSerive.selectDynamicAll();
            List<Dynamic> blockedDynamics = dynamicSerive.selectDynamicByState(-1);
            myResult.changeStatus(true);
            Map<String, Object> map = new HashMap<>(4);
            map.put("allUsers", allUsers.size());
            map.put("blockedUsers", blockedUsers.size());
            map.put("allDynamics", allDynamics.size());
            map.put("blockedDynamics", blockedDynamics.size());
            myResult.add("message", map);
        }

        return myResult;
    }


    @PostMapping("/reportedUser")
    @ApiOperation("获取所有被封用户基本信息")
    //参数userStates可以不要，因为是查找被封用户，所以这个state一定是1
    public MyResult reportedUser(@RequestParam String adminId, @RequestParam int userStatus) {
        MyResult myResult = new MyResult();
        if (!verifyId(adminId, myResult)) return myResult;

        Administrator administrator = administratorSerive.queryUserbyId(adminId);
        if (administrator == null) {
            myResult.changeStatus(false);
            myResult.add("message", "该管理员不存在");
        } else if (userStatus != 0) {
            myResult.changeStatus(false);
            myResult.add("message", "调用接口状态不为被封");
        } else {
            myResult.changeStatus(true);
            List<User> blockedUsers = userService.selectUserByState(userStatus);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (User user : blockedUsers) {
                Map<String, Object> map = new HashMap<>(4);
                map.put("userId", user.getUserId());
                map.put("userName", user.getUserName());
                map.put("tipOffNum", user.getTipOffNum());
                map.put("userState", user.getUserState());
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
                map.put("dynamicId", dynamic.getDynamicId());
                map.put("userId", dynamic.getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatarUrl", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());
                map.put("appendixType", dynamic.getDynamicType());
                if (dynamic.getDynamicType().equals("0")) {
                    map.put("text", dynamic.getDynamicContent());
                } else if (dynamic.getDynamicType().equals("1")) {
                    if (dynamic.getDynamicContent().contains(";")) {
                        String[] urls = dynamic.getDynamicContent().split(";");
                        for (int i = 0; i < urls.length; i++) {
                            String url = urls[i];
                            map.put("photoUrl" + (i + 1), url);
                        }
                    } else {
                        map.put("photoUrl", dynamic.getDynamicContent());
                    }
                } else if (dynamic.getDynamicType().equals("2")) {
                    map.put("videoUrl", dynamic.getDynamicContent());
                } else if (dynamic.getDynamicType().equals("3")) {
                    map.put("program", dynamic.getDynamicContent());
                }
                map.put("thumbNum", dynamic.getThumbNum());
                map.put("commentNum", dynamic.getCommentNum());
                map.put("tag", dynamic.getDynamicIndex1());
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
            myResult.add("message", "您用户状态传递错啦！！！");
        } else {
            myResult.changeStatus(true);
            List<Dynamic> dynamics = dynamicSerive.selectDynamicByState(dynamicState);
            List<Map<String, Object>> tmp = new ArrayList<>();
            for (Dynamic dynamic : dynamics) {
                Map<String, Object> map = new HashMap<>();
                List<User> user = userService.selectUserbyId(dynamic.getUserId());
                map.put("dynamicId", dynamic.getDynamicId());
                map.put("userId", dynamic.getUserId());
                map.put("userName", user.get(0).getUserName());
                map.put("userAvatarUrl", user.get(0).getAvatarURL());
                map.put("time", dynamic.getDynamicTime());
                if (dynamic.getDynamicType().equals("0")) {
                    map.put("text", dynamic.getDynamicContent());
                } else if (dynamic.getDynamicType().equals("1")) {
                    if (dynamic.getDynamicContent().contains(";")) {
                        String[] urls = dynamic.getDynamicContent().split(";");
                        for (int i = 0; i < urls.length; i++) {
                            String url = urls[i];
                            map.put("photoUrl" + (i + 1), url);
                        }
                    } else {
                        map.put("photoUrl", dynamic.getDynamicContent());
                    }
                } else if (dynamic.getDynamicType().equals("2")) {
                    map.put("videoUrl", dynamic.getDynamicContent());
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
                map.put("status",dynamicState);
                myResult.add("message",map);
            } else {
                myResult.changeStatus(false);
                myResult.add("message", "该动态没有被封");
            }
        }
        return myResult;

    }

}
