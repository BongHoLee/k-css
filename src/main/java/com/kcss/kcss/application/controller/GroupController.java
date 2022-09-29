package com.kcss.kcss.application.controller;


import static java.util.stream.Collectors.toList;

import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.GroupDTO;
import com.kcss.kcss.application.dto.GroupDTO.Info;
import com.kcss.kcss.domain.service.group.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Operation(summary = "그룹 등록", description = "id, condition, description 정보로 계정을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "그룹 등록 완료"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 그룹 등록 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 그룹 등록 실패")})
    @PostMapping
    public BaseResponse register(@RequestBody @Valid GroupDTO.RegisterRequest request) {
        groupService.register(request.convert());
        return BaseResponse.success();
    }

    @Operation(summary = "그룹 목록 조회", description = "그룹을 조회합니다. 특정 그룹 조회 시 해당 id가 필요합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료"),
            @ApiResponse(responseCode = "204", description = "검색된 결과가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 조회 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 조회 실패")})
    @GetMapping
    public List<GroupDTO.Info> find(@RequestParam(required = false) Long id) {
        if (id == null) {
            return groupService.findAllRegistered().stream().map(Info::of).collect(toList());
        } else {
            return Collections.singletonList(Info.of(groupService.findGroupOf(id)));
        }
    }

    @Operation(summary = "그룹 삭제", description = "주어진 id에 해당하는 그룹을 삭제합니다.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "그룹 삭제 완료")})
    @DeleteMapping
    public BaseResponse remove(@RequestParam(required = false) @NotNull @NotBlank Long id) {
        groupService.remove(id);
        return BaseResponse.success();
    }
}