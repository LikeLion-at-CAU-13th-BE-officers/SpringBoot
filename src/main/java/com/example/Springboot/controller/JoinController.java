package com.example.Springboot.controller;

import com.example.Springboot.config.PrincipalHandler;
import com.example.Springboot.domain.Member;
import com.example.Springboot.dto.request.JoinRequestDto;
import com.example.Springboot.dto.response.TokenResponseDto;
import com.example.Springboot.jwt.JwtTokenProvider;
import com.example.Springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/join")
    public void join(@RequestBody JoinRequestDto joinRequestDto) {
        memberService.join(joinRequestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody JoinRequestDto joinRequestDto) {
        Member member = memberService.login(joinRequestDto);
        return TokenResponseDto.of(jwtTokenProvider.generateAccessToken(member.getName()), jwtTokenProvider.generateRefreshToken(member.getName()));
    }

    @GetMapping("/my")
    public ResponseEntity<String> getMyName() {
        String name = PrincipalHandler.getNameFromPrincipal();
        return ResponseEntity.ok(name);
    }
}
