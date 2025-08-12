package com.siukatech.poc.spring.jpa.demo.many2many.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Setter
@Getter
@ToString
@Entity
@Table(name = "tbl_m2m_categories")
public class CategoryM2mEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(name = "category_id2")
    private String categoryId;

    @Column
    private String name;

    //
    // joinColumns - @JoinColumn
    // - name is the column in the @JoinTable, e.g. tbl_m2m_product_category_joins.category_id3
    // - referencedColumnName is the column in the current table, e.g. tbl_m2m_categories.category_id2
    //
    // inverseJoinColumns - @JoinColumn
    // - name is the column in the @JoinTable, e.g. tbl_m2m_product_category_joins.product_id3
    // - referencedColumnName is the column in the current table, e.g. tbl_m2m_products.product_id2
    @ManyToMany
    @JoinTable(name = "tbl_m2m_product_category_joins", joinColumns = {
            @JoinColumn(name = "category_id3", referencedColumnName = "category_id2")
    }, inverseJoinColumns = {
            @JoinColumn(name = "product_id3", referencedColumnName = "product_id2")
    })
    private List<ProductM2mEntity> productM2mEntities;

}
