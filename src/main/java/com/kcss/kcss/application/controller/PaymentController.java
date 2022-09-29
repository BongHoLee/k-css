package com.kcss.kcss.application.controller;


import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.PaymentDTO;
import com.kcss.kcss.domain.service.payment.PaymentService;
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
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "결제 처리", description = "결제를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 처리 완료"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 결제 처리 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 결제 처리 실패")})
    @PostMapping
    public BaseResponse pay(@RequestBody @Valid PaymentDTO.PayRequest request) {
        paymentService.pay(request.convert());
        return BaseResponse.success();
    }

    @Operation(summary = "결제 내역 조회", description = "등록된 ID로 그룹을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료"),
            @ApiResponse(responseCode = "204", description = "검색된 결과가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST, 조회 실패"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR, 조회 실패")})
    @GetMapping
    public PaymentDTO.Info findPayment(@RequestParam @NotNull @NotEmpty Long id) {
        return PaymentDTO.Info.of(paymentService.paymentOf(id));

    }
}