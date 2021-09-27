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
@Api(tags = "用户管理相关接口")
@RequestMapping("/User")
public class RegisterController {

    @Resource
    UserService userService;

    @PostMapping("/register")

    @ApiOperation("添加用户的接口")

    public Map<String, Object> register(@RequestParam String userId,@RequestParam String password) {
        Map<String, Object> map = new HashMap<>(3);

        if(userId.equals("")||password.equals("")){
            map.put("error","账号/密码不能为空");
            map.put("success", false);
            return map;
        }
        //判断userId是否已存在
        List<User> userList =userService.queryUserbyId(userId);
        if(userList.isEmpty()){
            User user=new User();
            user.setUserId(userId);
            user.setPassword(password);
            userService.insertUser(user);
            //注册成功>>跳转
            //map.setViewName("/logIn");

        }
            return map;
    }
}
