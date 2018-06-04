package com.marcin.phone;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring Framework annotation-driven configuration.
 * 
 * @author dream-tree
 * @version 3.00, January-May 2018
 */
@PropertySource("classpath:menu.properties")
@Configuration
@ComponentScan
public class BeanConfig {

}
