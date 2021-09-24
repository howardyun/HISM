package com.HISM.backfront.Controller;

import com.HISM.backfront.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    public int logIn(@RequestParam String userId, String password) {
                return userService.test(12);
    }

}
