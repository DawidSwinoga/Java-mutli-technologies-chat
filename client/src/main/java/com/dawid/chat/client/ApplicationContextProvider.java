package com.dawid.chat.client;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by Dawid on 21.11.2018 at 21:27.
 */
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        ApplicationContextProvider.context = context;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBeanByName(String name) {
        return (T) context.getBean(name);
    }
}

