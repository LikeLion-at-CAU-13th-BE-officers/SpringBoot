package com.example.Springboot.service;

import com.example.Springboot.domain.Member;
import com.example.Springboot.enums.Role;
import com.example.Springboot.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach //테스트 전 데이터 초기화
    void setUp() {
        memberRepository.deleteAll(); //기존 DB 데이터 모두 삭제

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .age(20 + i) // 예시로 21부터 50까지 나이 넣기
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10); // 첫번째 페이지에 10명 존재 하는지
        assertThat(page.getTotalElements()).isEqualTo(30); // 전체가 30개인지
        assertThat(page.getTotalPages()).isEqualTo(3); // 3페이지가 맞는지
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }


    // MemberServiceTest.java
    @Test
    void testGetAdultMembersSortedByName() {

        Page<Member> page = memberService.getAdultMembersSortedByName(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30); // assuming all members are age >= 20
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    @Test
    void testGetMembersByNamePrefix() {
        Page<Member> page = memberService.getMembersByNamePrefix("user2", 0, 10);
        // assertThat(page.getContent()).hasSize(11); // user2, user20~29
        assertThat(page.getContent()).hasSize(10); // user2, user20~29
        assertThat(page.getContent().get(0).getName()).startsWith("user2");
    }

}
