package com.HISM.backfront.Controller;


import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//必填
@Api(tags = "管理员操作接口")
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserService userService;

    @PostMapping("/findBlockUser")
    //必填
    @ApiOperation("添加用户的接口")
    public Map<String, Object> findBlockUser() {
        Map<String, Object> map = new HashMap<>(3);

        return map;
    }


}
