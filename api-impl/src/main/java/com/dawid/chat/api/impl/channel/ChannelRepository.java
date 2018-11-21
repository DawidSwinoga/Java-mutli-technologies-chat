package com.dawid.chat.api.impl.channel;

import com.dawid.chat.api.impl.user.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by Dawid on 17.11.2018 at 15:12.
 */

@Repository
public interface ChannelRepository {
    Channel createChannel(String channelName, User user);

    Optional<Channel> getById(String id);

    Collection<Channel> getAll();

    void removeFromChannels(User user);
}
