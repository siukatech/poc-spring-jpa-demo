package com.siukatech.poc.spring.jpa.demo.one2many.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@Entity
@Table(name = "tbl_o2m_product_category_joins")
public class ProductCategoryJoinO2mEntity {
    @EmbeddedId
    private ProductCategoryJoinO2mEmbedded productCategoryJoinO2mEmbedded;

    @ManyToOne
    @JoinColumn(name = "product_id3", referencedColumnName = "product_id2", insertable = false, updatable = false)
    private ProductO2mEntity productO2mEntity;

    @ManyToOne
    @JoinColumn(name = "category_id3", referencedColumnName = "category_id2", insertable = false, updatable = false)
    private CategoryO2mEntity categoryO2mEntity;

}
