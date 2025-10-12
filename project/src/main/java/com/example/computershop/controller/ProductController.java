package com.example.computershop.controller;

import com.example.computershop.model.Product;
import com.example.computershop.model.ProductDetail;
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
        @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // *** เพิ่มโค้ดส่วนนี้เข้าไป ***
        // ตรวจสอบเผื่อว่าสินค้าเก่าใน DB ไม่มี detail
        if (product.getProductDetail() == null) {
            ProductDetail detail = new ProductDetail();
            product.setProductDetail(detail);
            detail.setProduct(product);
        }
        // **************************
        model.addAttribute("product", product);
        return "update_product"; // หรือจะเปลี่ยนให้ไปที่ admin ก็ได้
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
    

    // Method นี้จะทำหน้าที่คล้ายๆกับหน้าแรก แต่จะส่งไปที่หน้า admin.html แทน
    @GetMapping("/admin")
    public String showAdminPanel(Model model) {
    // ดึงรายการสินค้าทั้งหมด
    model.addAttribute("listProducts", productService.findAll());
    // เตรียม object เปล่าๆ สำหรับฟอร์ม "เพิ่มสินค้า"
    model.addAttribute("product", new Product());
    return "admin"; // บอกให้ไปเปิดไฟล์ admin.html
    }
}