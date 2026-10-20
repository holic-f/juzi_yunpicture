package com.yupi.yunpicture;

import org.apache.shardingsphere.spring.boot.ShardingSphereAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

//@SpringBootApplication
@SpringBootApplication(exclude = {ShardingSphereAutoConfiguration.class})
@MapperScan("com.yupi.yunpicture.infrastructure.mapper")
@EnableAsync
@EnableAspectJAutoProxy(exposeProxy = true)
public class YunPictureApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunPictureApplication.class, args);
    }

}
