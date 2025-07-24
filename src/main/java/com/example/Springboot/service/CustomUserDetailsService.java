package com.example.Springboot.service;

import com.example.Springboot.domain.Member;
import com.example.Springboot.repository.MemberRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Member member = memberRepository.findByName(name)
            .orElseThrow(() -> new IllegalArgumentException(name + "은 존재하지 않는 사용자입니다."));

        return new User(member.getName(), member.getPassword(), new ArrayList<>());
    }
}
