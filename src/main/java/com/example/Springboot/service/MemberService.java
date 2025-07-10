package com.example.Springboot.service;

import com.example.Springboot.domain.Member;
import com.example.Springboot.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
}
