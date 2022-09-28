package com.kcss.kcss.application.dto;

import com.kcss.kcss.domain.model.account.Account;
import com.kcss.kcss.domain.model.account.vo.Age;
import com.kcss.kcss.domain.model.account.vo.Residence;
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
    public static class Response {
        private Long id;
        private String residence;
        private Long age;

        public static Response from(Account account) {
            return Response.builder()
                    .id(account.getId())
                    .residence(account.getResidence().residenceName())
                    .age(account.getAge().age())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Request {
        @NotNull
        private Long age;
        @NotNull
        private String residence;

        public Account convert() {
            return Account.builder().age(Age.of(age)).residence(Residence.of(residence)).build();
        }
    }
}
