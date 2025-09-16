package com.example.Springboot.domain;

import com.example.Springboot.domain.Mapping.ProductOrders;
import com.example.Springboot.enums.DeliverStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "deleted = false")
public class Orders extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DeliverStatus deliverStatus; // 배송상태

    @ManyToOne
    @JoinColumn(name ="buyer_id")
    private Member buyer;

    @OneToMany(mappedBy="orders", cascade=CascadeType.ALL)
    @Builder.Default
    private List<ProductOrders> productOrders = new ArrayList<>();

    @OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
    private Coupon coupon;

    @Embedded
    private ShippingAddress shippingAddress; // 배송정보

    // 삭제 여부
    @Builder.Default
    private boolean deleted = false;

    // 배송정보 변경
    public void changeShippingAddress(ShippingAddress newAddress) {
        this.shippingAddress = newAddress;
    }

    // 주문 삭제
    public void markDeleted() {
        this.deleted = true;
    }
}
