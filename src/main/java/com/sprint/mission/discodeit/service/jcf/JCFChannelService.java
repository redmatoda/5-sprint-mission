package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;


public class JCFChannelService implements ChannelService {

    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public void createChannel(Channel channel) {
        data.put(channel.getId(), channel);
    }

    @Override
    public Channel getChannelById(UUID channelId) {
        return data.get(channelId);
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID channelId, long updatedAt) {
        Channel channel = data.get(channelId);
        if (channel != null) {
            channel.updateTimestamp(updatedAt);
        }
    }

    @Override
    public void deleteChannel(UUID channelId) {
        data.remove(channelId);
    }
}