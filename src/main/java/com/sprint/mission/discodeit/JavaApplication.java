package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.NoSuchElementException;

public class JavaApplication {

    public static void userTest(UserService userService){
        System.out.println("==============User================");
        // 생성 실험
        User user1 = userService.createUser("홍길동", "1234");
        User user2 = userService.createUser("김길동", "1234");
        System.out.println("유저 생성 : " + user1);
        System.out.println("유저 생성 : " + user2);

        User findId = userService.find(user1.getId());
        System.out.println("유저 찾기 : " + findId);

        System.out.println("모든 유저 : " + userService.findAll());
        System.out.println("총 인원 : " + userService.findAll().size() + "명");

        System.out.println(String.format("|%s의 메시지 업데이트|",user1.getUsername()));
        User userUpdate = userService.update(user1.getId(),"김아무개" , "1122");
        System.out.println("업데이트 유저ID " + user1.getId() + "\n업데이트 이름: " + userUpdate.getUsername() + "\n업데이트 비번: " + userUpdate.getPassword() );

        userService.delete(user1.getId());
        System.out.println(String.format("%s 삭제!",user1.getUsername()));

        try {
            User deleteUser = userService.find(user1.getId());
            if (deleteUser == null) {
                System.out.println("✅ [User] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
            } else {
                System.out.println("❌ [User] 삭제 실패. 조회 결과: " + deleteUser.getId());
            }
        } catch (NoSuchElementException e) {
            System.out.println("✅ [User] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
        }

        System.out.println("==============User================");

    }

    public static void messageTest(MessageService messageService){
        System.out.println("==============Message================");
        Message message1 = messageService.createMsg("비가 많이 오네요." , "동구라미방", "동구라미");
        System.out.println("메시지 생성 : " + message1);

        Message findId = messageService.find(message1.getId());
        System.out.println("메시지(유저) 찾기 : " + findId + "\n내용 : " + message1.getContent() + "\n채널명: " + message1.getChannelId() +
                "\n작성자: " + message1.getAuthorId());

        System.out.println("모든 메시지(유저) : " + messageService.findAll());
        System.out.println("총 인원 : " + messageService.findAll().size() + "명");

        System.out.println(String.format("|%s의 메시지 업데이트|",message1.getAuthorId()));
        Message messageUpdate = messageService.update(message1.getId(),"날이 많이 덥네요" , "스타벅스","김아무개");
        System.out.println("내용: " + messageUpdate.getContent() + "\n채널명: " + messageUpdate.getChannelId() +
                "\n작성자: " + messageUpdate.getAuthorId());

        messageService.delete(message1.getId());
        System.out.println(String.format("%s의 메시지 삭제!",message1.getAuthorId()));

        try {
            Message deleteMessage = messageService.find(message1.getId());
            if (deleteMessage == null) {
                System.out.println("✅ [Message] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
            } else {
                System.out.println("❌ [Message] 삭제 실패. 조회 결과: " + deleteMessage.getId());
            }
        } catch (NoSuchElementException e) {
            System.out.println("✅ [Message] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
        }

        System.out.println("==============Message================");


    }

    public static void channelTest(ChannelService channelService){
        System.out.println("==============Channel================");
        Channel channel1 = channelService.createChannel("비오는 날은 칼국수방");
        System.out.println("채널 생성 : " + channel1);

        Channel findId = channelService.find(channel1.getId());
        System.out.println("채널 찾기 : " + findId);

        System.out.println("모든 메시지(유저) : " + channelService.findAll());
        System.out.println("총 인원 : " + channelService.findAll().size() + "명");

        System.out.println(String.format("|%s채널 채널명 업데이트|",channel1.getName()));
        Channel channelUpdate = channelService.update(channel1.getId(),"국밥방");
        System.out.println("채널명: " + channelUpdate.getName());

        channelService.delete(channel1.getId());
        System.out.println(String.format("%s 삭제!",channel1.getName()));

        try {
            Channel deleteChannel = channelService.find(channel1.getId());
            if (deleteChannel == null) {
                System.out.println("✅ [Channel] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
            } else {
                System.out.println("❌ [Channel] 삭제 실패. 조회 결과: " + deleteChannel.getId());
            }
        } catch (NoSuchElementException e) {
            System.out.println("✅ [Channel] 삭제됨. 해당 ID로 더 이상 조회되지 않습니다.");
        }


    }
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        MessageService messageService = new JCFMessageService();
        ChannelService channelService = new JCFChannelService();

//        userTest(userService);
//        messageTest(messageService);
        channelTest(channelService);
    }
}
