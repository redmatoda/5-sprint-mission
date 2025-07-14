package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public void createUser(User user) {
        data.put(user.getId(), user);
    }

    @Override
    public User getUserById(UUID userId) {
        return data.get(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateUser(UUID userId, long updatedAt) {
        User user = data.get(userId);
        if (user != null) {
            user.updateTimestamp(updatedAt);
        }
    }

    @Override
    public void deleteUser(UUID userId) {
        data.remove(userId);
    }
}
