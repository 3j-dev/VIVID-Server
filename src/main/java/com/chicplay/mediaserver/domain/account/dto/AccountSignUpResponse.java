package com.chicplay.mediaserver.domain.account.dto;

import com.chicplay.mediaserver.domain.account.domain.Account;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountSignUpResponse {

    private String email;
    private String name;

    public AccountSignUpResponse(Account account){
        this.email = account.getEmail();
        this.name = account.getName();
    }
}
