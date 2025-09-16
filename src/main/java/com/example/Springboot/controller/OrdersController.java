package com.example.Springboot.controller;

import com.example.Springboot.dto.request.OrderRequestDto;
import com.example.Springboot.dto.request.OrderUpdateRequestDto;
import com.example.Springboot.dto.response.OrderResponseDto;
import com.example.Springboot.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public OrderResponseDto create(@RequestBody OrderRequestDto dto) {
        return OrderResponseDto.fromEntity(ordersService.createOrder(dto));
    }

    @GetMapping
    public List<OrderResponseDto> myOrders(@RequestParam Long buyerId) {
        return ordersService.findByBuyer(buyerId)
                .stream()
                .map(OrderResponseDto::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public OrderResponseDto detail(@PathVariable Long id) {
        return OrderResponseDto.fromEntity(ordersService.findOne(id));
    }

    @PutMapping("/{id}/shipping")
    public void updateShipping(@PathVariable Long id,
                               @RequestBody OrderUpdateRequestDto dto) {
        ordersService.updateShipping(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ordersService.softDelete(id);
    }
}