package com.example.Springboot.domain;

import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingAddress {

    private String receiver;

    private String phone;

    private String zipcode; // 우편번호

    private String addr1; // 도로명주소

    private String addr2; // 상세주소

    public static ShippingAddress of(String receiver, String phone, String zipcode, String addr1, String addr2) {
        return ShippingAddress.builder()
                .receiver(receiver)
                .phone(phone)
                .zipcode(zipcode)
                .addr1(addr1)
                .addr2(addr2)
                .build();
    }
}