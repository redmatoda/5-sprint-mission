package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    @Operation(summary = "User 등록")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User가 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "같은 email 또는 username을 사용하는 User가 이미 존재함",
            content = @Content(examples = @ExampleObject(value = ("user with email {newEmail} already exists"))))
    })
    @RequestMapping(path = "create",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // ResponseEntity -> HTTP 응답의 상태코드, 헤더, 바디를 세밀하게 제어할 때 사용함.
    public ResponseEntity<User> create( // 사용자 등록
            @RequestPart UserCreateRequest userCreateRequest,
            @RequestPart(required = false) MultipartFile profile
            )throws IOException {
        Optional<BinaryContentCreateRequest> binaryContentCreateRequest = Optional.empty();
        if(profile != null && !profile.isEmpty()){
            binaryContentCreateRequest = Optional.of(new BinaryContentCreateRequest(
                    profile.getOriginalFilename(),
                    profile.getContentType(),
                    profile.getBytes()
            ));
        }
        User user = userService.create(userCreateRequest, binaryContentCreateRequest);
        return ResponseEntity.ok(user);

    }

    @Operation(summary = "User 정보 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User 정보가 성공적으로 수정됨"),
            @ApiResponse(responseCode = "400", description = "같은 email 또는 username를 사용하는 User가 이미 존재함",
            content = @Content(examples = @ExampleObject(value = ("user with email {newEmail} already exists")))),
            @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음",
            content = @Content(examples = @ExampleObject(value = ("User with id {userId} not found")))),
    })
    @RequestMapping(path = "/{userId}", // 사용자 수정(415 에러발생)
            method =  RequestMethod.PATCH,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> update(@PathVariable("userId") UUID userId,
                                       @RequestPart("request") UserUpdateRequest request,
                                       @RequestPart(required = false) MultipartFile file) throws IOException {

        // 변환해서 BinaryContentCreateRequest로 만들기
        Optional<BinaryContentCreateRequest> profileCreateRequest = Optional.empty();
        if (!file.isEmpty()) {
            profileCreateRequest = Optional.of(new BinaryContentCreateRequest(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            ));
        }
        User userUpdate = userService.update(userId, request, profileCreateRequest);
        return ResponseEntity.ok(userUpdate);
    }

    @Operation(summary = "User 삭제")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User가 성공적으로 삭제됨"),
            @ApiResponse(responseCode = "404", description = "User를 찾을 수 없음",
            content = @Content(examples = @ExampleObject(value = ("User with id {userId} not found"))))
    })
    @RequestMapping(path = "/{userId}", method = RequestMethod.DELETE) // 사용자 제거
    public ResponseEntity<Void> delete(@PathVariable("userId") UUID userId) {
        userService.delete(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @Operation(summary = "전체 User 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User 목록 조회 성공")
    })
    @RequestMapping(path = "/findAll") // 모든 사용자 조회
    public ResponseEntity<List<UserDto>> findAll(){
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @RequestMapping(
            path = "/{userId}/userStatus",
            method = RequestMethod.PATCH
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User 온라인 상태가 성공적으로 업데이트됨"),
            @ApiResponse(responseCode = "200", description = "해당 User의 UserStatus를 찾을 수 없음",
            content = @Content(examples = @ExampleObject(value = ("UserStatus with userId {userId} not found"))))
    })
    // 온라인 업데이트 (415 에러)
    public ResponseEntity<UserStatus> updateUserStatusByUserId(
            @PathVariable UUID userId,
            @RequestBody UserStatusUpdateRequest request
    ) {
        UserStatus userStatus = userStatusService.update(userId, request);
        return ResponseEntity.ok(userStatus);
    }
}
