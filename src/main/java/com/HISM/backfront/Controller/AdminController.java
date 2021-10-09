package com.HISM.backfront.Controller;


import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.AdministratorSerive;
import com.HISM.backfront.Service.UserService;
import com.HISM.backfront.domain.Administrator;
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
    @Resource
    AdministratorSerive administratorSerive;

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

    @PostMapping("/findBlockUser")
    //必填
    @ApiOperation("")
    public Map<String, Object> findBlockUser() {
        Map<String, Object> map = new HashMap<>(3);

        return map;
    }


}
