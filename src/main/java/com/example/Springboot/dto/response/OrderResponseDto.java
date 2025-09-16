package com.example.Springboot.dto.response;

import com.example.Springboot.domain.Orders;
import com.example.Springboot.domain.Mapping.ProductOrders;
import com.example.Springboot.enums.DeliverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private DeliverStatus deliverStatus;
    private ShippingAddressDto shippingAddress;
    private List<ProductItemDto> products;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ShippingAddressDto {
        private String receiver;
        private String phone;
        private String zipcode;
        private String addr1;
        private String addr2;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ProductItemDto {
        private Long productId;
        private String name;
        private Integer quantity;
    }

    public static OrderResponseDto fromEntity(Orders order) {
        return OrderResponseDto.builder()
                .id(order.getId())
                .deliverStatus(order.getDeliverStatus())
                .shippingAddress(new ShippingAddressDto(
                        order.getShippingAddress().getReceiver(),
                        order.getShippingAddress().getPhone(),
                        order.getShippingAddress().getZipcode(),
                        order.getShippingAddress().getAddr1(),
                        order.getShippingAddress().getAddr2()
                ))
                .products(order.getProductOrders().stream()
                        .map(po -> new ProductItemDto(
                                po.getProduct().getId(),
                                po.getProduct().getName(),
                                po.getQuantity()))
                        .toList())
                .build();
    }
}
