package com.example.Springboot.service;

import com.example.Springboot.domain.Member;
import com.example.Springboot.dto.request.JoinRequestDto;
import com.example.Springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Page<Member> getMembersByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findAll(pageable);
    }

    // 1. 나이 20 이상, 이름 오름차순
    public Page<Member> getAdultMembersSortedByName(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return memberRepository.findByAgeGreaterThanEqualOrderByNameAsc(20, pageable);
    }

    // 2. 이름 시작 필터링
    public Page<Member> getMembersByNamePrefix(String prefix, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.findByNameStartingWith(prefix, pageable);
    }

    // 비밀번호 인코더 DI(생성자 주입)
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void join(JoinRequestDto joinRequestDto) {
        // 해당 name이 이미 존재하는 경우
        if (memberRepository.existsByName(joinRequestDto.getName())) {
            return; // 나중에는 예외 처리
        }

        // 유저 객체 생성
        Member member = joinRequestDto.toEntity(bCryptPasswordEncoder);

        // 유저 정보 저장
        memberRepository.save(member);
    }

    public Member login(JoinRequestDto joinRequestDto) {
        Member member = memberRepository.findByName(joinRequestDto.getName())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!bCryptPasswordEncoder.matches(joinRequestDto.getPassword(), member.getPassword())) {
            return null;
        }

        return member;
    }
}
