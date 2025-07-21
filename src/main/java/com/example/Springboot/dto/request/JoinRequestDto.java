package com.example.Springboot.dto.request;

import com.example.Springboot.domain.Member;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
public class JoinRequestDto {
    private String name;
    private String password;

    public Member toEntity(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
            .name(this.name)
            .password(bCryptPasswordEncoder.encode(this.password))
            .build();
    }
}
