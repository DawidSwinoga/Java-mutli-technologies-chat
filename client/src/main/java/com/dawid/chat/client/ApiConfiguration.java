package com.dawid.chat.client;

import com.dawid.chat.api.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

/**
 * Created by Dawid on 21.11.2018 at 19:40.
 */

@Configuration
public class ApiConfiguration {
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


    @Bean
    ApplicationContextProvider applicationContextProvider() {
        return new ApplicationContextProvider();
    }
}
