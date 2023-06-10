package com.lad.config;

import com.lad.utils.FileUploadUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan({"com.lad.service","com.lad.dao"})
@Import({JdbcConfig.class, MybatisConfig.class, WebConfig.class})
@EnableTransactionManagement
public class SpringConfig {

    @Bean
    public FileUploadUtil fileUploadUtil() {
        return new FileUploadUtil();
    }

}
