package com.example.Springboot.dto.request;

import com.example.Springboot.domain.Member;
import com.example.Springboot.domain.Product;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private Long memberId;

    public Product toEntity(Member seller) {
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .seller(seller)
                .build();
    }
}
