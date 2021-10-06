package com.HISM.backfront.Controller;

import com.HISM.backfront.Config.WebAppConfig;
import com.HISM.backfront.Result.MyResult;
import com.sun.imageio.plugins.common.ImageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;

@RestController
//必填
@Api(tags = "用户管理相关接口")
@RequestMapping("/hello")
public class HelloController {
    @Resource
    WebAppConfig webAppConfig;

    @PutMapping("/article/img/upload")
    public MyResult uploadImg(@RequestParam("editormd-image-file") MultipartFile multipartFile)  {
        MyResult myResult=new MyResult();
        String contentType = multipartFile.getContentType();
        String root_fileName = multipartFile.getOriginalFilename();
        //获取路径
        String filePath = webAppConfig.location ;
        String file_name = null;
        try {
            file_name = saveImg(multipartFile, filePath);
        } catch (IOException e) {

        }
        return myResult;
    }

    /**
     * 保存文件，直接以multipartFile形式
     * @param multipartFile
     * @param path 文件保存绝对路径
     * @return 返回文件名
     * @throws IOException
     */
    public static String saveImg(MultipartFile multipartFile,String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
        String fileName = "test" + ".png";
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(path + File.separator + fileName));
        byte[] bs = new byte[1024];
        int len;
        while ((len = fileInputStream.read(bs)) != -1) {
            bos.write(bs, 0, len);
        }
        bos.flush();
        bos.close();
        return fileName;
    }


}