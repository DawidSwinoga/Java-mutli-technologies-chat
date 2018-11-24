package com.dawid;

import com.dawid.chat.api.ChatService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Created by Dawid on 16.11.2018 at 16:08.
 */

@Configuration
@EnableAsync
public class ServiceConfiguration {
    @Bean(name = "/h_chat")
    RemoteExporter hessianService(ChatService service) {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }

    @Bean(name = "/b_chat")
    RemoteExporter burlapService(ChatService service) {
        BurlapServiceExporter exporter = new BurlapServiceExporter();
        exporter.setService(service);
        exporter.setServiceInterface(ChatService.class);
        return exporter;
    }
}
