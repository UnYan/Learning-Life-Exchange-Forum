package com.a.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("index");
        registry.addViewController("/main").setViewName("home");
        registry.addViewController("/userspace").setViewName("userspace");
        registry.addViewController("/setting").setViewName("setting");
        registry.addViewController("/mk").setViewName("editor/editormd" );
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/retrivepassword").setViewName("retrivepassword");
        registry.addViewController("/retrievepassword").setViewName("retrievepassword");
        registry.addViewController("/Course").setViewName("Course");
        registry.addViewController("/chat").setViewName("chat");
        registry.addViewController("/chatting").setViewName("chatting");
        registry.addViewController("/administrators").setViewName("administrators");
        registry.addViewController("/otheruserspace").setViewName("otheruserspace");
        registry.addViewController("/firstUse").setViewName("firstUse");
        registry.addViewController("/touristmain").setViewName("touristmain");
        registry.addViewController("/coffee").setViewName("coffee");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/UploadImages/**").addResourceLocations("file:/home/yzx/springboot/uploadImg/");
        registry.addResourceHandler("**").addResourceLocations("classpath:/static/");
    }
}
