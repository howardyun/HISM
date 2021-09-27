package com.HISM.backfront.Controller;

import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/User")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/logIn")
    //必填
    @ApiOperation("添加用户的接口")
    public Map<String, Object> logIn(@RequestParam String userId,@RequestParam String password) {

        Map<String, Object> map = new HashMap<>(3);
        //判断用户id或者密码是否为空
        if ("".equals(userId) || "".equals(password)) {
            map.put("success", false);
            map.put("message", "用户id或用户密码为空");
            return map;
        }
        List<User> userList =userService.queryUserbyId(userId);
        if (userList.size()==0){
            map.put("success", false);
            map.put("message", "没有该用户信息");
        }else if(userList.size()==1){
            User user=userList.get(0);
            if(password.equals(user.getPassword())){
                map.put("success", true);
                map.put("message", "成功登录");
            }
            else {
                map.put("success", false);
                map.put("message", "密码错误");
            }
        }else {
            map.put("success", false);
            map.put("message", "用户信息多于一个");
        }
        return map;
    }






}
