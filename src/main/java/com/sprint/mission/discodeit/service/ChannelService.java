package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void createChannel(Channel channel);
    Channel getChannelById(UUID channelId);
    List<Channel> getAllChannels();
    void updateChannel(UUID channelId, long updatedAt);
    void deleteChannel(UUID channelId);
}
