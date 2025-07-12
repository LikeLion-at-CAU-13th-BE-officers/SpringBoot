package com.example.Springboot.controller;

import com.example.Springboot.domain.Member;
import com.example.Springboot.dto.request.ProductDeleteRequestDto;
import com.example.Springboot.dto.request.ProductRequestDto;
import com.example.Springboot.dto.request.ProductUpdateRequestDto;
import com.example.Springboot.dto.response.ProductResponseDto;
import com.example.Springboot.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductUpdateRequestDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,
                                                @RequestBody ProductDeleteRequestDto dto) {
        productService.deleteProduct(id, dto);
        return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
    }


}