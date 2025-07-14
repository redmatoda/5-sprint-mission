package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.*;

public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();


        System.out.println("===== USER =====");

        // 등록
        User user = new User();
        userService.createUser(user);
        System.out.println("유저 생성: " + user.getId());

        // 단건 조회
        User foundUser = userService.getUserById(user.getId());
        System.out.println("단건 조회: " + foundUser.getId());

        // 다건 조회
        List<User> allUsers = userService.getAllUsers();
        System.out.println("다건 조회: " + allUsers.size());

        // 수정
        userService.updateUser(user.getId(), System.currentTimeMillis());

        // 수정된 데이터 조회
        User updatedUser = userService.getUserById(user.getId());
        System.out.println("업데이트 유저ID: " + updatedUser.getId());

        // 삭제
        userService.deleteUser(user.getId());

        // 삭제 후 확인
        User deletedUser = userService.getUserById(user.getId());
        System.out.println("유저가 존재하는지? " + (deletedUser != null));

        User deletedUser2 = userService.getUserById(user.getId());

        if (deletedUser2 == null) {
            System.out.println("✅ [User] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
        } else {
            System.out.println("❌ [User] 삭제 실패. 조회 결과: " + deletedUser2.getId());
        }

        // ---------------------------------------

    }
}
