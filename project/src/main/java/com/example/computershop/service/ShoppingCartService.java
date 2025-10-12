package com.example.computershop.service;

import com.example.computershop.dto.CartItemDto;
import com.example.computershop.model.Product;
import com.example.computershop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SessionScope // สำคัญมาก! ทำให้ Service นี้มีชีวิตอยู่แค่ใน Session ของผู้ใช้แต่ละคน
public class ShoppingCartService {

    @Autowired
    private ProductRepository productRepository;

    // ใช้ Map เพื่อเก็บสินค้าในตะกร้า โดยมี Key เป็น Product ID
    private Map<Long, CartItemDto> cartItems = new HashMap<>();

    // เมธอดสำหรับเพิ่มสินค้าลงตะกร้า
    public void addProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        CartItemDto cartItem = cartItems.get(productId);
        if (cartItem == null) {
            // ถ้ายังไม่มีสินค้านี้ในตะกร้า ให้เพิ่มใหม่
            cartItems.put(productId, new CartItemDto(product, 1));
        } else {
            // ถ้ามีอยู่แล้ว ให้เพิ่มจำนวนขึ้น 1
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
    }

    // เมธอดสำหรับลบสินค้าออกจากตะกร้า
    public void removeProduct(Long productId) {
        cartItems.remove(productId);
    }

    // เมธอดสำหรับอัปเดตจำนวนสินค้า
    public void updateProductQuantity(Long productId, int quantity) {
        CartItemDto cartItem = cartItems.get(productId);
        if (cartItem != null && quantity > 0) {
            cartItem.setQuantity(quantity);
        } else if (quantity <= 0) {
            removeProduct(productId);
        }
    }

    // เมธอดสำหรับดึงรายการสินค้าทั้งหมดในตะกร้า
    public List<CartItemDto> getCartItems() {
        return new ArrayList<>(cartItems.values());
    }

    // เมธอดสำหรับคำนวณราคารวม
    public double getTotalPrice() {
        return cartItems.values().stream()
                .mapToDouble(CartItemDto::getTotalPrice)
                .sum();
    }
}