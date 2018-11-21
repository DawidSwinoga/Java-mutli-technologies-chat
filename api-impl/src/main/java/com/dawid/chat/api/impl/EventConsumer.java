package com.dawid.chat.api.impl;

import reactor.bus.Event;
import reactor.fn.Consumer;

/**
 * Created by Dawid on 17.11.2018 at 23:01.
 */
public interface EventConsumer<T> extends Consumer<Event<T>> {
}
