package com.dawid.chat.client;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.commons.ChatEventPublisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import java.util.UUID;

/**
 * Created by Dawid on 21.11.2018 at 19:40.
 */

@Configuration
@EnableJms
public class ApiConfiguration {
    public static final String QUEUE_DESTINATION_NAME = UUID.randomUUID().toString();

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

    @Bean
    AbstractJmsListeningContainer jmsListeningContainer(MessageListener messageListener, ConnectionFactory connectionFactory) {
        DefaultMessageListenerContainer defaultMessageListenerContainer = new DefaultMessageListenerContainer();
        defaultMessageListenerContainer.setMessageListener(messageListener);
        defaultMessageListenerContainer.setConnectionFactory(connectionFactory);
        defaultMessageListenerContainer.setDestination(new ActiveMQQueue(QUEUE_DESTINATION_NAME));
        return defaultMessageListenerContainer;
    }

    @Bean
    ChatEventPublisher chatEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        return new ChatEventPublisher(applicationEventPublisher);
    }
}
