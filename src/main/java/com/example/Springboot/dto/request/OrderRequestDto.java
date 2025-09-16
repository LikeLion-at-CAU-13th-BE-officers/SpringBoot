package com.example.Springboot.dto.request;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderRequestDto {

    private Long buyerId;   // 구매자 id
    private ShippingAddressDto shippingAddress;
    private List<OrderProductDto> products;

    @Getter
    public static class ShippingAddressDto {
        private String receiver;
        private String phone;
        private String zipcode;
        private String addr1;
        private String addr2;
    }

    @Getter
    public static class OrderProductDto {
        private Long productId;
        private Integer quantity;
    }
}
