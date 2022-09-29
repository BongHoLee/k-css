package com.kcss.kcss.application.controller;


import static java.util.stream.Collectors.toList;

import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.GroupDTO;
import com.kcss.kcss.application.dto.GroupDTO.Info;
import com.kcss.kcss.domain.service.group.GroupService;
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


    @GetMapping
    public List<GroupDTO.Info> find(@RequestParam(required = false) Long id) {
        if (id == null) {
            return groupService.findAllRegistered().stream().map(Info::of).collect(toList());
        } else {
            return Collections.singletonList(Info.of(groupService.findGroupOf(id)));
        }
    }

    @PostMapping
    public BaseResponse register(@RequestBody @Valid GroupDTO.RegisterRequest request) {
        groupService.register(request.convert());
        return BaseResponse.success();
    }

    @DeleteMapping
    public BaseResponse remove(@RequestParam(required = false) @NotNull @NotBlank Long id) {
        groupService.remove(id);
        return BaseResponse.success();
    }
}