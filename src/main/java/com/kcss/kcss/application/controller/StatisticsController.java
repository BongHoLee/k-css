package com.kcss.kcss.application.controller;


import com.kcss.kcss.application.dto.StatisticsDTO;
import com.kcss.kcss.domain.service.group.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "결제 그룹 집계", description = "등록된 groupId에 해당하는 결제 그룹의 집계를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료"),
            @ApiResponse(responseCode = "204", description = "검색된 결과가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 조회 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 조회 실패")})
    @GetMapping
    public StatisticsDTO find(@RequestParam @NotNull @NotBlank Long groupId) {
        return StatisticsDTO.of(groupService.statisticsOf(groupId));
    }
}