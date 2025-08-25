package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class UserStatus extends BaseEntity {
    private UUID userId;
    private Instant lastActiveAt;

    public UserStatus(UUID userId, Instant lastActiveAt) {
        super();
        this.userId = userId;
        this.lastActiveAt = lastActiveAt;
    }

    public boolean isOnline() {
        return lastActiveAt != null &&
                Duration.between(lastActiveAt, Instant.now()).toMinutes() <= 5;
    }

    public void update(Instant newSeenAt) {
        if (newSeenAt.isAfter(this.lastActiveAt)) {
            this.lastActiveAt = newSeenAt;
            this.updatedAt = Instant.now();
        }
    }

}
