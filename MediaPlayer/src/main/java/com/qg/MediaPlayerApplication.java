package com.qg;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author qg
 *
 */
@SpringBootApplication
@Configuration  
@ComponentScan  
@EnableAutoConfiguration
public class MediaPlayerApplication {
	@Bean
	 public MultipartConfigElement multipartConfigElement() {
		    MultipartConfigFactory factory = new MultipartConfigFactory();
		    factory.setMaxFileSize("1024MB"); // KB,MB
		    factory.setMaxRequestSize("500MB");
		    return factory.createMultipartConfig();
		  }
	 
	public static void main(String[] args) {
		SpringApplication.run(MediaPlayerApplication.class, args);
	}
}
