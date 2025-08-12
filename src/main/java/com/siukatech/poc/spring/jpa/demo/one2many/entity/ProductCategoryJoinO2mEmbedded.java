package com.siukatech.poc.spring.jpa.demo.one2many.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ProductCategoryJoinO2mEmbedded implements Serializable {
    @Column(name = "product_id3")
    private String productId;
    @Column(name = "category_id3")
    private String categoryId;
}
