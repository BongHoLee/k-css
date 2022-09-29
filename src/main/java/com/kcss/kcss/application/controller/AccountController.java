package com.kcss.kcss.application.controller;

import com.kcss.kcss.application.dto.AccountDTO;
import com.kcss.kcss.application.dto.AccountDTO.Info;
import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.domain.service.account.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "계정 등록", description = "id, age, residence 정보로 계정을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "계정 등록 완료"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 계정 등록 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 계정 등록 실패")})
    @PostMapping
    public BaseResponse registerAccount(@RequestBody @Valid AccountDTO.RegisterRequest registerRequest) {
        accountService.register(registerRequest.convert());
        return BaseResponse.success();
    }

    @Operation(summary = "계정 조회", description = "등록된 ID로 계정을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료"),
            @ApiResponse(responseCode = "204", description = "검색된 결과가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 조회 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 조회 실패")})
    @GetMapping
    public Info findAccount(@RequestParam @NotNull @NotEmpty Long id) {
        return Info.of(accountService.findAccountOf(id));

    }
}
