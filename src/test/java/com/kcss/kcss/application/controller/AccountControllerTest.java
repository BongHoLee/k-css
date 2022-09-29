package com.kcss.kcss.application.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kcss.kcss.application.dto.AccountDTO.RegisterRequest;
import com.kcss.kcss.application.dto.AccountDTO.Info;
import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import com.kcss.kcss.domain.service.account.AccountService;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AccountController.class)
@ActiveProfiles("test")
class AccountControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Test
    @DisplayName("계정 생성 API 호출 테스트")
    void Account_생성_API_호출_테스트() throws Exception {
        given(accountService.register(any()))
                .willReturn(Account.builder().id(1L).residence(Residence.BUSAN).age(Age.of(30L)).build());

        RegisterRequest q = RegisterRequest.builder().id(1L).age(30L).residence("부산").build();
        String requestContents = objectMapper.writeValueAsString(q);
        String responseContents = objectMapper.writeValueAsString(BaseResponse.success());
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(requestContents))
                .andExpect(status().isOk())
                .andExpect(content().string(responseContents))
                ;
    }

    @Test
    @DisplayName("계정 조회 API 호출 테스트")
    void Account_조회_API_호출_테스트() throws Exception {
        given(accountService.findAccountOf(any()))
                .willReturn(Account.builder().id(1L).residence(Residence.BUSAN).age(Age.of(30L)).build());

        Info q = Info.builder().id(1L).age(30L).residence("부산").payments(new ArrayList<>()).build();
        String response = objectMapper.writeValueAsString(q);
        mockMvc.perform(get("/account")
                        .param("id", "1")
                        .characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().string(response))
        ;
    }

    @Test
    @DisplayName("계정 조회 ID 누락 시 Valid 검증 테스트")
    void Account_조회_ID_누락시_검증() throws Exception {
        BaseResponse response = BaseResponse.errorOf(ApplicationErrorCode.INVALID_INPUT_VALUE);
        String resStr = objectMapper.writeValueAsString(response);
        mockMvc.perform(get("/account")
                        .characterEncoding("UTF-8"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(resStr))
        ;
    }

    @Test
    @DisplayName("계정 생성 정보  누락 시 Valid 검증 테스트")
    void Account_생성_정보_누락시_검증() throws Exception {

        RegisterRequest q = RegisterRequest.builder().id(1L).age(30L).build();
        String requestContents = objectMapper.writeValueAsString(q);
        String responseContents = objectMapper.writeValueAsString(BaseResponse.errorOf(ApplicationErrorCode.INVALID_INPUT_VALUE));
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestContents))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(responseContents))
        ;
    }
}