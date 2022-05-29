package com.HISM.backfront;

import com.HISM.backfront.Tools.RsaTool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.NoSuchAlgorithmException;


@MapperScan("com.HISM.backfront.mapper")
@EnableSwagger2
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SpringApplication.run(DemoApplication.class, args);
        RsaTool.RsaKeyPair rsaKeyPair=RsaTool.generateKeyPair();
        RsaTool.publicKey=rsaKeyPair.getPublicKey();
        RsaTool.privateKey=rsaKeyPair.getPrivateKey();
        System.out.println(RsaTool.publicKey);
        System.out.println(RsaTool.privateKey);
    }

}
