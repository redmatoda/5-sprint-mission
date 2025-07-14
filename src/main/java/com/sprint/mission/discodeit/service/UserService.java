package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void createUser(User user);
    User getUserById(UUID userid);
    List<User> getAllUsers();
    void updateUser(UUID userid, long updatedAt);
    void deleteUser(UUID userid);
}
