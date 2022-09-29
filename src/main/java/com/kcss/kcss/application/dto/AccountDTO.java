package com.kcss.kcss.application.dto;

import static java.util.stream.Collectors.toList;

import com.kcss.kcss.application.dto.PaymentDTO.Info;
import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccountDTO {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private Long id;
        private String residence;
        private Long age;
        private List<PaymentDTO.Info> payments;

        public static Info of(Account account) {
            return Info.builder()
                    .id(account.getId())
                    .residence(account.getResidence().residenceName())
                    .age(account.getAge().age())
                    .payments(account.getPayments().stream().map(PaymentDTO.Info::of).collect(toList()))
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class RegisterRequest {
        @NotNull
        private Long id;
        @NotNull
        private Long age;
        @NotNull
        private String residence;

        public Account convert() {
            return Account.builder().id(id).age(Age.of(age)).residence(Residence.of(residence)).build();
        }
    }

}
