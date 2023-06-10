package com.lad.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan({"com.lad.controller","com.lad.config"})
@EnableWebMvc
public class SpringMvcConfig {
}



