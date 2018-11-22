package com.dawid.chat.client;

import lombok.Getter;

/**
 * Created by Dawid on 21.11.2018 at 20:51.
 */
@Getter
public enum CommunicationMethod {
    HESSIAN("hessian"),
    BURLAP("burlap"),
    XML_RPC("xmlRpc");

    final String beanName;

    CommunicationMethod(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
