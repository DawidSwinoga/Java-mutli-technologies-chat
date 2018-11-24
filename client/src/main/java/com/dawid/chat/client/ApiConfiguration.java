package com.dawid.chat.client;

import com.dawid.chat.api.ChatService;
import com.dawid.chat.client.rest.RestChatApi;
import com.dawid.chat.commons.ChatEventPublisher;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.listener.AbstractJmsListeningContainer;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.remoting.caucho.BurlapProxyFactoryBean;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

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
    public HessianProxyFactoryBean hessianInvoker(@Value("${api.hessian}") String hessianApi) {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl(hessianApi);
        invoker.setServiceInterface(ChatService.class);
        return invoker;
    }

    @Bean("burlap")
    public BurlapProxyFactoryBean burlapInvoker(@Value("${api.burlap}") String burlapApi) {
        BurlapProxyFactoryBean invoker = new BurlapProxyFactoryBean();
        invoker.setServiceUrl(burlapApi);
        invoker.setServiceInterface(ChatService.class);
        return invoker;
    }

    @Bean("rest")
    public ChatService restChatService(@Value("${api}") String hostName, RestTemplate restTemplate) {
        return new RestChatApi(restTemplate, hostName);
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

    @Bean
    RestTemplate restTemplate(ResponseErrorHandler responseErrorHandler) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(responseErrorHandler);
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        return restTemplate;
    }
}
