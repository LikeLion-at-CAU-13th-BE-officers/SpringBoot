package com.example.Springboot.dto.request;

import lombok.Getter;

@Getter
public class OrderUpdateRequestDto {
    private String receiver;
    private String phone;
    private String zipcode;
    private String addr1;
    private String addr2;
}