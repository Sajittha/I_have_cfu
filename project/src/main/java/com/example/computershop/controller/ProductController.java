package com.example.computershop.controller;

import com.example.computershop.model.Product;
import com.example.computershop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // แสดงสินค้าทั้งหมด (Read)
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listProducts", productService.findAll());
        return "index";
    }

    // แสดงฟอร์มเพิ่มสินค้า (Create)
    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "new_product";
    }

    // บันทึกสินค้า (Create/Update)
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.save(product);
        return "redirect:/";
    }

    // แสดงฟอร์มแก้ไขสินค้า (Update)
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "update_product";
    }

    // ลบสินค้า (Delete)
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        productService.deleteById(id);
        return "redirect:/";
    }
}