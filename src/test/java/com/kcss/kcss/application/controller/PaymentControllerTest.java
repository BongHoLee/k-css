package com.kcss.kcss.application.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.PaymentDTO.Info;
import com.kcss.kcss.application.dto.PaymentDTO.PayRequest;
import com.kcss.kcss.application.error.ApplicationErrorCode;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import com.kcss.kcss.domain.service.payment.PaymentService;
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

@WebMvcTest(PaymentController.class)
@ActiveProfiles("test")
class PaymentControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

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
    @DisplayName("결제 생성 API 호출 테스트")
    void Payment_생성_API_호출_테스트() throws Exception {

        PayRequest payRequest = PayRequest.builder()
                .paymentId(1L)
                .accountId(1L)
                .amount(3000.0)
                .itemCategory("패션")
                .methodType("카드")
                .region("부산")
                .build();

        String requestContents = objectMapper.writeValueAsString(payRequest);
        String responseContents = objectMapper.writeValueAsString(BaseResponse.success());

        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestContents))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(responseContents))
        ;
    }

    @Test
    @DisplayName("결제 생성 API 호출 누락 데이터 존재 시 예외 테스트")
    void Payment_생성_API_호출_예외_테스트() throws Exception {

        PayRequest payRequest = PayRequest.builder()
                .paymentId(1L)
                .accountId(1L)
                .itemCategory("패션")
                .methodType("카드")
                .region("부산")
                .build();

        String requestContents = objectMapper.writeValueAsString(payRequest);
        String responseContents = objectMapper.writeValueAsString(
                BaseResponse.errorOf(ApplicationErrorCode.INVALID_INPUT_VALUE));

        mockMvc.perform(post("/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(requestContents))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(responseContents))
        ;
    }

    @Test
    @DisplayName("결제 내역 조회 API 호출 테스트")
    void Payment_조회_API_호출_테스트() throws Exception {
        Payment payment = Payment.builder()
                .id(1L)
                .account(Account.builder().id(1L).build())
                .methodType(MethodType.CARD)
                .itemCategory(ItemCategory.BOOK)
                .amount(Amount.of(30.0))
                .region(Region.CHUNGNAM)
                .build();
        given(paymentService.paymentOf(any()))
                .willReturn(payment);


        String response = objectMapper.writeValueAsString(Info.of(payment));
        mockMvc.perform(get("/payment")
                        .param("id", "1")
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(response))
        ;
    }

}