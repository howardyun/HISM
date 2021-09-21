package com.HISM.backfront.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/dynamic")
public class DynamicController {
    @PostMapping("/getMoments")
    //必填
    @ApiOperation("添加用户的接口")
    public String hello(@RequestParam String name) {
        return name;
    }

}
