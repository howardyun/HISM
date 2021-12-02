package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.HISM.backfront.Service.GeneralService;
import com.sun.imageio.plugins.common.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/hello")
public class HelloController {
    @Resource
    WebAppConfig webAppConfig;

    @Resource
    GeneralService generalService;

    @PutMapping("/article/img/upload")
    public MyResult uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile) {
        MyResult myResult = new MyResult();
        String contentType = multipartFile.getContentType();
        String root_fileName = multipartFile.getOriginalFilename();
        //获取路径
        String filePath = webAppConfig.location;
        String file_name = null;
        try {
            file_name = saveImg(multipartFile, filePath);
        } catch (IOException e) {

        }
        return myResult;
    }

    @PostMapping("/test/saveImg")
    public String saveImg(MultipartFile multipartFile, String path) throws IOException {
        Map<String,String> t=new HashMap<String, String>();
        t.put("path","test");
        t.put("token","123");
        t.put("fileName","test.jpeg");
       String s = generalService.doPostFormData(multipartFile,t);
       System.out.print(s);
        return "test";
    }


}