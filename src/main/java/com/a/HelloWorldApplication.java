package com.a;

import com.a.Server.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class HelloWorldApplication {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(HelloWorldApplication.class, args);
        NettyServer nettyServer = context.getBean(NettyServer.class);
        nettyServer.start();
    }
}
