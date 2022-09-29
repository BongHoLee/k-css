package com.kcss.kcss.application.controller;

import com.kcss.kcss.application.dto.AccountDTO;
import com.kcss.kcss.application.dto.AccountDTO.Info;
import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.domain.service.account.AccountService;
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

    @GetMapping
    public Info findAccount(@RequestParam @NotNull @NotEmpty Long id) {
        return Info.of(accountService.findAccountOf(id));

    }

    @PostMapping
    public BaseResponse registerAccount(@RequestBody @Valid AccountDTO.RegisterRequest registerRequest) {
        accountService.register(registerRequest.convert());
        return BaseResponse.success();
    }
}
