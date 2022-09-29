package com.kcss.kcss.application.dto;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.payment.Payment;
import com.kcss.kcss.domain.model.payment.vo.Amount;
import com.kcss.kcss.domain.model.payment.vo.ItemCategory;
import com.kcss.kcss.domain.model.payment.vo.MethodType;
import com.kcss.kcss.domain.model.payment.vo.Region;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PaymentDTO {

    @Getter
    @Setter
    @Builder
    public static class Info {
        private Long paymentId;
        private Long accountId;
        private Double amount;
        private String methodType;
        private String itemCategory;
        private String region;

        public static Info of(Payment payment) {
            return Info.builder()
                    .paymentId(payment.getId())
                    .accountId(payment.getAccount().getId())
                    .amount(payment.getAmount().amount())
                    .methodType(payment.getMethodType().methodTypeName())
                    .itemCategory(payment.getItemCategory().categoryName())
                    .region(payment.getRegion().regionName())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class PayRequest {
        @NotNull
        private Long paymentId;
        @NotNull
        private Long accountId;
        @NotNull
        private Double amount;
        @NotNull
        private String methodType;
        @NotNull
        private String itemCategory;
        @NotNull
        private String region;

        public Payment convert() {
            return Payment.builder()
                    .id(paymentId)
                    .account(Account.builder().id(accountId).build())
                    .amount(Amount.of(amount))
                    .methodType(MethodType.kor(methodType))
                    .itemCategory(ItemCategory.of(itemCategory))
                    .region(Region.of(region))
                    .build();
        }
    }


}
