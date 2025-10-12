package com.example.computershop.controller;

import com.example.computershop.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    // แสดงหน้ารายการสินค้าในตะกร้า
    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", shoppingCartService.getCartItems());
        model.addAttribute("totalPrice", shoppingCartService.getTotalPrice());
        return "cart"; // ไปที่หน้า cart.html
    }

    // เพิ่มสินค้าลงตะกร้า
    @GetMapping("/cart/add/{productId}")
    public String addToCart(@PathVariable("productId") Long productId) {
        shoppingCartService.addProduct(productId);
        return "redirect:/"; // กลับไปหน้าแรก
    }

    // อัปเดตจำนวนสินค้า
    @PostMapping("/cart/update")
    public String updateCartItem(@RequestParam("productId") Long productId, @RequestParam("quantity") int quantity) {
        shoppingCartService.updateProductQuantity(productId, quantity);
        return "redirect:/cart";
    }

    // ลบสินค้าออกจากตะกร้า
    @GetMapping("/cart/remove/{productId}")
    public String removeFromCart(@PathVariable("productId") Long productId) {
        shoppingCartService.removeProduct(productId);
        return "redirect:/cart";
    }
}