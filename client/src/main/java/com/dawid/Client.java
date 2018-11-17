package com.dawid;


import com.dawid.chat.api.ChatService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import static java.lang.System.out;

@SpringBootApplication(exclude = {SpringApplicationAdminJmxAutoConfiguration.class})
@Configuration
public class Client {

    @Bean("hessian")
    public HessianProxyFactoryBean hessianInvoker() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/h_chat");
        invoker.setServiceInterface(ChatService.class);
        return invoker;
    }

    @Bean("burlap")
    public BurlapProxyFactoryBean burlapInvoker() {
        BurlapProxyFactoryBean invoker = new BurlapProxyFactoryBean();
        invoker.setServiceUrl("http://localhost:8080/b_chat");
        invoker.setServiceInterface(ChatService.class);
        return invoker;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Client.class, args);
        ChatService hessian = context
                .getBean("hessian", ChatService.class);
        System.out.println("hessian");
        out.println(hessian.loginUser("hessian"));

        System.out.println("\nburlap");
        ChatService burlap = context
                .getBean("burlap", ChatService.class);
        System.out.println(burlap.loginUser("burlap"));
    }
}
