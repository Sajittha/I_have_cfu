package com.example.computershop.model;

import jakarta.persistence.*;
import lombok.Data;

//(One-to-One)
@Data
@Entity
public class ProductDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpu;
    private String ram;
    private String storage;
    private String gpu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}