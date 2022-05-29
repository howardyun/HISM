package com.HISM.backfront.Service;


import com.HISM.backfront.Config.WebAppConfig;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.Map;

@Service
public class GeneralService {

    @Resource
    WebAppConfig webAppConfig;

    /**
     *保存文件接口,传入文件multipartFile，已经paramMap
     * @param multipartFile
	 * @param paramMap 存储http请求的参数Map 本服务为filepath，filename，token(default=123).
     * @return java.lang.String
     * @author ysx
     * @creed: Talk is cheap,show me the code
     * @date 2022/4/27 6:55 下午
     */
    public String saveImg(MultipartFile multipartFile, Map<String, String> paramMap) throws IOException {
        //地址
        String url = webAppConfig.fileServer;

        // 创建Http实例
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建HttpPost实例
        HttpPost httpPost = new HttpPost(url);

        // 请求参数配置
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000)
                .setConnectionRequestTimeout(10000).build();
        httpPost.setConfig(requestConfig);

        try {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(java.nio.charset.Charset.forName("UTF-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

            String fileName = multipartFile.getOriginalFilename();
            // 文件流
            builder.addBinaryBody("multipartFile", multipartFile.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);
            //表单中其他参数
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                builder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.create("text/plain", Consts.UTF_8)));
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 返回
                String res = EntityUtils.toString(response.getEntity(), java.nio.charset.Charset.forName("UTF-8"));
                return res;
            }else
            {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用失败");
            return null;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    System.out.println("关闭HttpPost连接失败！");
                    return null;
                }
            }
        }

    }


//    public String

}
