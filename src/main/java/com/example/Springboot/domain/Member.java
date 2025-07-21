package com.example.Springboot.domain;

import com.example.Springboot.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;
    private String address;
    private String email;
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role; // 판매자면 SELLER, 구매자면 BUYER

    private Boolean isAdmin; // 관리자 계정 여부

    private Integer deposit; // 현재 계좌 잔액

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    private Integer age;

    // 추후 계좌 잔액 더하기, 빼기 메서드 추가 필요 => service 계층에 넣을지, 여기 둘지 고민...
    public void chargeDeposit(int money){
        this.deposit += money;
    }
    public void useDeposit(int money) {
        this.deposit -= money;
    }

    @Builder
    public Member(String name, String password, String address, String email, String phoneNumber,
                  Role role, Boolean isAdmin, Integer deposit, Integer age) {
        this.name = name;
        this.password = password;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.isAdmin = isAdmin;
        this.deposit = deposit;
        this.age=age;
    }

    public boolean isSeller() {
        return Role.SELLER.equals(this.role);
    }

}