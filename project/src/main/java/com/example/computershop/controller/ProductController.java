package com.example.computershop.controller;

import com.example.computershop.model.Product;
import com.example.computershop.model.ProductDetail; // เพิ่ม import นี้
import com.example.computershop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    // --- แสดงหน้าหลัก (สำหรับลูกค้า) ---
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("listProducts", productService.findAll());
        return "index";
    }

    // --- แสดงหน้ารายละเอียดสินค้า 1 ชิ้น ---
    @GetMapping("/product/{id}")
    public String viewProductDetail(@PathVariable("id") Long id, Model model) {
        Product product = productService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        return "product-detail";
    }

    // --- แสดงหน้าจัดการสินค้า (Admin Panel) ---
    @GetMapping("/admin")
    public String showAdminPanel(Model model) {
        model.addAttribute("listProducts", productService.findAll());
        
        Product product = new Product();
        // สร้าง ProductDetail ที่ว่างเปล่าผูกติดไปด้วยเสมอ
        ProductDetail detail = new ProductDetail();
        product.setProductDetail(detail);
        detail.setProduct(product);
        
        model.addAttribute("product", product);
        return "admin";
    }

    // --- แสดงฟอร์มสำหรับแก้ไขสินค้า (ในหน้า Admin) ---
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {
        Product product = productService.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));

        // ตรวจสอบเพื่อให้แน่ใจว่า Product มี ProductDetail เสมอ
        if (product.getProductDetail() == null) {
            ProductDetail detail = new ProductDetail();
            product.setProductDetail(detail);
            detail.setProduct(product);
        }
        
        model.addAttribute("product", product);
        model.addAttribute("listProducts", productService.findAll());
        return "admin";
    }

    // --- บันทึกข้อมูล (ใช้ทั้งการเพิ่มและการแก้ไข) ---
    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        // ตั้งค่าความสัมพันธ์สองทางก่อนบันทึก
        if (product.getProductDetail() != null) {
            product.getProductDetail().setProduct(product);
        }
        productService.save(product);
        return "redirect:/admin";
    }

    // --- ลบสินค้า ---
    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(value = "id") long id) {
        this.productService.deleteById(id);
        return "redirect:/admin";
    }
}