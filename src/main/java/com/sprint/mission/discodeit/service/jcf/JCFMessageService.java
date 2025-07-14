package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> data;

    public JCFMessageService() {
        this.data = new HashMap<>();
    }

    @Override
    public void createMessage(Message message) {
        data.put(message.getId(), message);
    }

    @Override
    public Message getMessageById(UUID messageId) {
        return data.get(messageId);
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateMessage(UUID messageId, long updatedAt) {
        Message message = data.get(messageId);
        if (message != null) {
            message.updateTimestamp(updatedAt);
        }
    }

    @Override
    public void deleteMessage(UUID messageId) {
        data.remove(messageId);
    }
}
