package com.marcin.phone;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:menu.properties")
@Configuration
@ComponentScan
public class BeanConfig {

}

