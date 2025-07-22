package com.example.Springboot.controller;

import com.example.Springboot.dto.request.JoinRequestDto;
import com.example.Springboot.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody JoinRequestDto joinRequestDto) {
        String username = memberService.join(joinRequestDto);
        return ResponseEntity.ok(username+" 회원가입 되었습니다.");
    }

}
