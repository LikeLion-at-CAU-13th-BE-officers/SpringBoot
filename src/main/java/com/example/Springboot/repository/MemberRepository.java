package com.example.Springboot.repository;

import com.example.Springboot.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByName(String name);

    // 나이 >= 20
    Page<Member> findByAgeGreaterThanEqualOrderByNameAsc(int age, Pageable pageable);

    // 이름이 주어진 값으로 시작
    Page<Member> findByNameStartingWith(String prefix, Pageable pageable);

    // 이름 중복 검사 쿼리
    boolean existsByName(String name);
}