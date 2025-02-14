package com.example.Springboot.domain;

import com.example.Springboot.domain.Mapping.ProductOrders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 상품이름
    private Integer price; // 상품가격
    private Integer stock; // 상품 재고
    private String description; // 상품 정보

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Member seller;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductOrders> productOrders;

    // 추후 stock 줄이는 로직 필요 => service 계층에 넣을지, 여기 넣을지는 고민...
}
