package com.example.Springboot.service;

import com.example.Springboot.domain.Mapping.ProductOrders;
import com.example.Springboot.domain.Member;
import com.example.Springboot.domain.Orders;
import com.example.Springboot.domain.Product;
import com.example.Springboot.domain.ShippingAddress;
import com.example.Springboot.dto.request.OrderRequestDto;
import com.example.Springboot.dto.request.OrderUpdateRequestDto;
import com.example.Springboot.enums.DeliverStatus;
import com.example.Springboot.repository.MemberRepository;
import com.example.Springboot.repository.OrdersRepository;
import com.example.Springboot.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    public Orders createOrder(OrderRequestDto dto) {
        Member buyer = memberRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        int totalAmount = dto.getProducts().stream()
                .mapToInt(p -> {
                    Product product = productRepository.findById(p.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + p.getProductId()));
                    return product.getPrice() * p.getQuantity();
                })
                .sum();

        // 잔액 확인 및 차감
        buyer.withdraw(totalAmount);

        ShippingAddress address = ShippingAddress.of(
                dto.getShippingAddress().getReceiver(),
                dto.getShippingAddress().getPhone(),
                dto.getShippingAddress().getZipcode(),
                dto.getShippingAddress().getAddr1(),
                dto.getShippingAddress().getAddr2()
        );

        Orders order = Orders.builder()
                .buyer(buyer)
                .deliverStatus(DeliverStatus.PREPARATION) // 최초 주문 상태
                .shippingAddress(address)
                .build();

        List<ProductOrders> productOrders = dto.getProducts().stream()
                .map(p -> {
                    Product product = productRepository.findById(p.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

                    product.reduceStock(p.getQuantity()); // 재고 차감

                    return ProductOrders.builder()
                            .orders(order)
                            .product(product)
                            .quantity(p.getQuantity())
                            .build();
                }).toList();

        order.getProductOrders().addAll(productOrders);

        return ordersRepository.save(order);
    }

    // 주문 목록 조회
    @Transactional(readOnly = true)
    public List<Orders> findByBuyer(Long buyerId) {
        return ordersRepository.findByBuyerId(buyerId);
    }

    // 주문 상세 조회
    @Transactional(readOnly = true)
    public Orders findOne(Long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
    }

    public void updateShipping(Long id, OrderUpdateRequestDto dto) {
        Orders order = findOne(id);
        if (order.getDeliverStatus() != DeliverStatus.PREPARATION) {
            throw new IllegalStateException("배송이 시작되어 수정이 불가능합니다.");
        }

        order.changeShippingAddress(ShippingAddress.of(
            dto.getReceiver(), dto.getPhone(),
            dto.getZipcode(), dto.getAddr1(), dto.getAddr2()
            ));
    }

    public void softDelete(Long id) {
        Orders order = findOne(id);
        if (order.getDeliverStatus() != DeliverStatus.COMPLETED) {
            throw new IllegalStateException("배송 완료된 주문만 삭제 가능합니다.");
        }

        order.markDeleted();
    }
}