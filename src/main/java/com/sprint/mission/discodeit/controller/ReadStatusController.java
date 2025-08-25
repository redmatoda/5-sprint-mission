package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/readstatuses")
@Tag(name = "ReadStatus", description = "Message 읽음 상태 API")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @Operation(summary = "Message 읽음 상태 생성")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message 읽음 상태가 성공적으로 생성됨"),
            @ApiResponse(responseCode = "400", description = "이미 읽음 상태가 존재함",
            content = @Content(examples = @ExampleObject(value = "ReadStatus with userId {userId} and channelId {channelId} already exists"))),
            @ApiResponse(responseCode = "404", description = "Channel 또는 User를 찾을 수 없음",
            content = @Content(examples = @ExampleObject(value = ("Channel | User with id {channelId | userId} not found"))))
    })
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> create(@RequestBody ReadStatusCreateRequest request){
        Instant lastReadAt = request.lastReadAt() != null ? request.lastReadAt() : Instant.now();
        ReadStatus createStatus = readStatusService.create(request);
        return ResponseEntity.ok(createStatus);
    }

    @Operation(summary = "Message 읽음 상태 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message 읽음 상태가 성공적으로 수정됨"),
            @ApiResponse(responseCode = "404", description = "ReadStatus with id {readStatusId} not found")
    })
    @RequestMapping(path = "/{readStatusId}", method = RequestMethod.PATCH)
    public ResponseEntity<ReadStatus> update(@PathVariable("readStatusId") UUID id,
                                                       @RequestBody ReadStatusUpdateRequest request){
        ReadStatus updateStatus = readStatusService.update(id, request);
        return ResponseEntity.ok(updateStatus);
    }

    @Operation(summary = "User의 Message 읽음 상태 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message 읽음 상태 목록 조회 성공")
    })
    @RequestMapping(path ="/find", method = RequestMethod.GET)
    public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam UUID userId){
        List<ReadStatus> readStatus = readStatusService.findAllByUserId(userId);
        return ResponseEntity.ok(readStatus);
    }
}
