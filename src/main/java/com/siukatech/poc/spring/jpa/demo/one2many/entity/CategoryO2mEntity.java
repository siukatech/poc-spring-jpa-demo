package com.siukatech.poc.spring.jpa.demo.one2many.entity;

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
@Table(name = "tbl_o2m_categories")
public class CategoryO2mEntity {

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
    // @JoinColumn
    // - name is the column in the association table, e.g. tbl_o2m_product_category_joins.category_id3
    // - referencedColumnName is the column in the current table, e.g. tbl_o2m_categories.category_id2
    @OneToMany
    @JoinColumn(name = "category_id3", referencedColumnName = "category_id2")
    private List<ProductCategoryJoinO2mEntity> productCategoryJoinO2mEntities;

}
