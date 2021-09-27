package com.HISM.backfront.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//必填
@Api(tags = "动态管理接口")
@RequestMapping("/moments")
public class DynamicController {
    @PostMapping("/createMomentWithPhotos")
    //必填
    @ApiOperation("用户上传照片")
    public String createMomentWithPhotos(@RequestParam String name) {
        return name;
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
    public String createMomentWithCode(@RequestParam String name) {
        return name;
    }

    @PostMapping("/createMomentOnlyText")
    //必填
    @ApiOperation("用户上传文本")
    public String createMomentOnlyText(@RequestParam String name) {
        return name;
    }

}
