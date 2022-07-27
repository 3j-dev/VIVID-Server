package com.chicplay.mediaserver.domain.account.dto;

import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.domain.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountDto {

    @Valid
    private String email;

    @NotNull
    private String name;

    private String password;

    public AccountDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Account toEntity() {
        return Account.builder()
                .email(email)
                .name(name)
                .password(Password.builder().password(this.password).build())
                .build();
    }

}
