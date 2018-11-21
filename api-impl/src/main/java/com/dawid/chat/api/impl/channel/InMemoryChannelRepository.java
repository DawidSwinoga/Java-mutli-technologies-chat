package com.dawid.chat.api.impl.channel;

import com.dawid.chat.api.channel.ChannelAlreadyExistException;
import com.dawid.chat.api.impl.InMemoryRepository;
import com.dawid.chat.api.impl.user.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dawid on 17.11.2018 at 15:13.
 */

@Repository
public class InMemoryChannelRepository implements ChannelRepository, InMemoryRepository {
    private final Map<String, Channel> nameChannel = new ConcurrentHashMap<>();

    @Override
    public Channel createChannel(String channelName, User user) {
        String id = generateId();
        Channel channel = nameChannel.putIfAbsent(id, new Channel(id, channelName, user));
        if (!channel.isIdValid(id)) {
            throw new ChannelAlreadyExistException();
        }
        return channel;
    }

    @Override
    public Optional<Channel> getById(String id) {
        return nameChannel.values().stream().filter(channel -> channel.isIdValid(id)).findFirst();
    }

    @Override
    public Collection<Channel> getAll() {
        return nameChannel.values();
    }

    @Override
    public void removeFromChannels(User user) {
        nameChannel.values().forEach(it -> it.removeUser(user));
    }
}
