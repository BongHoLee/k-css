package com.kcss.kcss.application.controller;


import com.kcss.kcss.application.dto.AccountDTO;
import com.kcss.kcss.application.dto.BaseResponse;
import com.kcss.kcss.application.dto.PaymentDTO;
import com.kcss.kcss.domain.service.payment.PaymentService;
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

    @GetMapping
    public PaymentDTO.Info findPayment(@RequestParam @NotNull @NotEmpty Long id) {
        return PaymentDTO.Info.of(paymentService.paymentOf(id));

    }

    @PostMapping
    public BaseResponse pay(@RequestBody @Valid PaymentDTO.PayRequest request) {
        paymentService.pay(request.convert());
        return BaseResponse.success();
    }
}