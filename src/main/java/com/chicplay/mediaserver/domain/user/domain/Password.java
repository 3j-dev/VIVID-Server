package com.chicplay.mediaserver.domain.user.domain;

import com.chicplay.mediaserver.domain.user.exception.PasswordFailedExceededException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "password_failed_count", nullable = false)
    private int failedCount;

    @Builder
    public Password(String password) {
        this.password =  new BCryptPasswordEncoder().encode(password);
    }

    public boolean isMatched(final String rwaPwd){

        if (this.failedCount >= 5)
            throw new PasswordFailedExceededException();

        boolean matches = isMatches(rwaPwd);

        // update fail count
        if(matches)
            this.failedCount = 0;
        else
            this.failedCount++;

        return matches;
    }

    // 비밀번호 매치 여부 판단
    private boolean isMatches(String rawPwd){
        return new BCryptPasswordEncoder().matches(rawPwd,this.password);
    }

}
