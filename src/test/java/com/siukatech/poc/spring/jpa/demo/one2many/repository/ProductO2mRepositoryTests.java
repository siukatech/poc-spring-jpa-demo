package com.siukatech.poc.spring.jpa.demo.one2many.repository;

import com.siukatech.poc.spring.jpa.demo.one2many.entity.CategoryO2mEntity;
import com.siukatech.poc.spring.jpa.demo.one2many.entity.ProductO2mEntity;
import com.siukatech.poc.spring.jpa.demo.one2many.entity.TagO2mEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@Slf4j
@DataJpaTest
@TestPropertySource(properties = {
        "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
})
public class ProductO2mRepositoryTests {

    @Autowired
    private ProductO2mRepository productO2mRepository;

    @Autowired
    private CategoryO2mRepository categoryO2mRepository;

    @Autowired
    private TagO2mRepository tagO2mRepository;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_tagO2mEntity_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    private void prepare_tagO2mEntity_basic() {
        String tagId1 = "tag-1";
        TagO2mEntity tagO2mEntity1 = new TagO2mEntity();
        tagO2mEntity1.setTagId(tagId1);
        tagO2mEntity1.setName(tagId1);
        this.tagO2mRepository.saveAndFlush(tagO2mEntity1);
    }

    @Test
    public void test_entityO2mRelation_basic() {
        log.info("test_entityO2mRelation_basic - start");

        // select poe1_0.id,poe1_0.name,poe1_0.product_id2,poe1_0.version
        // from tbl_o2m_products poe1_0
        List<ProductO2mEntity> productO2mEntityList = this.productO2mRepository.findAll();

        // select distinct coe1_0.id,coe1_0.category_id2,coe1_0.name,coe1_0.version
        // from tbl_o2m_products poe1_0
        // join tbl_o2m_product_category_joins pcje1_0 on poe1_0.product_id2=pcje1_0.product_id3
        // join tbl_o2m_categories coe1_0 on coe1_0.category_id2=pcje1_0.category_id3
        List<CategoryO2mEntity> productCategoryO2mEntityList = this.productO2mRepository.findDistinctCategoryBy();

        // select coe1_0.id,coe1_0.category_id2,coe1_0.name,coe1_0.version
        // from tbl_o2m_categories coe1_0
        List<CategoryO2mEntity> categoryO2mEntityList = this.categoryO2mRepository.findAll();

        // select distinct poe1_0.id,poe1_0.name,poe1_0.product_id2,poe1_0.version
        // from tbl_o2m_categories coe1_0
        // join tbl_o2m_product_category_joins pcje1_0 on coe1_0.category_id2=pcje1_0.category_id3
        // join tbl_o2m_products poe1_0 on poe1_0.product_id2=pcje1_0.product_id3
        List<ProductO2mEntity> categoryProductO2mEntityList = this.categoryO2mRepository.findDistinctProductBy();

        log.info("test_entityO2mRelation_basic - start");
    }


}
