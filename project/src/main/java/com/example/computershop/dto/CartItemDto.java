package com.example.computershop.dto;

import com.example.computershop.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long productId;
    private String productName;
    private double price;
    private int quantity;
    private String imageUrl; 

    // แก้ไข constructor ให้ดึง imageUrl มาจาก Product ด้วย
    public CartItemDto(Product product, int quantity) {
        this.productId = product.getId();
        this.productName = product.getName();
        this.price = product.getPrice();
        this.quantity = quantity;
        this.imageUrl = product.getImageUrl(); 
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}