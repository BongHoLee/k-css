package com.kcss.kcss.application.controller;


import com.kcss.kcss.application.dto.StatisticsDTO;
import com.kcss.kcss.domain.service.group.GroupService;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private final GroupService groupService;

    public StatisticsController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping
    public StatisticsDTO find(@RequestParam @NotNull @NotBlank Long groupId) {
        return StatisticsDTO.of(groupService.statisticsOf(groupId));
    }
}