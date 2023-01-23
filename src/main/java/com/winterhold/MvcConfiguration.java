package com.winterhold;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/home/index");
            registry.addViewController("/author").setViewName("forward:/author/index");
            registry.addViewController("/book").setViewName("forward:/book/index");
            registry.addViewController("/customer").setViewName("forward:/customer/index");

        }
    }

