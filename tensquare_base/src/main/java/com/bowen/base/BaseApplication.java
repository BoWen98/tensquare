package com.bowen.base;

import com.bowen.common.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.base
 * @ClassName: BaseApplication
 * @Author: Bowen
 * @Description: 基础启动类
 * @Date: 2019/11/18 15:35
 * @Version: 1.0.0
 */
@SpringBootApplication
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
