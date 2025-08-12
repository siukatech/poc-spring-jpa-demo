package com.siukatech.poc.spring.jpa.demo.many2many.repository;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.TagM2mEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@DataJpaTest
@TestPropertySource(properties = {
        "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
})
public class CategoryM2mRepositoryTests {

    @Autowired
    private ProductM2mRepository productM2mRepository;

    @Autowired
    private CategoryM2mRepository categoryM2mRepository;

    @Autowired
    private TagM2mRepository tagM2mRepository;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_tagM2mEntity_basic();
        this.prepare_productCategoryJoinM2mEntity_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    private void prepare_productCategoryJoinM2mEntity_basic() {
        List<ProductM2mEntity> productM2mEntityListWip = new ArrayList<>();
        int productAllSize = (int) (Math.random() * 6);
        for (int i=0; i<productAllSize; i++) {
            String productId1 = "product-%s".formatted(i);
            ProductM2mEntity productM2mEntity1 = new ProductM2mEntity();
            productM2mEntity1.setProductId(productId1);
            productM2mEntity1.setName(productId1);
            productM2mEntity1.setCategoryM2mEntities(new ArrayList<>());
            productM2mEntityListWip.add(productM2mEntity1);
        }
        List<ProductM2mEntity> productM2mEntityList = this.productM2mRepository.saveAllAndFlush(productM2mEntityListWip);
        log.info("prepare_productCategoryJoinM2mEntity_basic - productAllSize: [{}], productM2mEntityList.size: [{}]"
                , productAllSize, productM2mEntityList.size());
        //
        List<CategoryM2mEntity> categoryM2mEntityListWip = new ArrayList<>();
        int categoryAllSize = (int) (Math.random() * 6);
        for (int i=0; i<categoryAllSize; i++) {
//            int productForCategorySize = (int) (Math.random() * productM2mEntityList.size());
            int productForCategorySize = productM2mEntityList.size();
            List<ProductM2mEntity> productM2mEntityListForCategory = new ArrayList<>();
            for (int j=0; j<productForCategorySize; j++) {
                productM2mEntityListForCategory.add(productM2mEntityList.get(j));
            }
            String categoryId1 = "category-%s".formatted(i);
            CategoryM2mEntity categoryM2mEntity1 = new CategoryM2mEntity();
            categoryM2mEntity1.setCategoryId(categoryId1);
            categoryM2mEntity1.setName(categoryId1);
            categoryM2mEntity1.setProductM2mEntities(productM2mEntityListForCategory);
            categoryM2mEntityListWip.add(categoryM2mEntity1);
            log.info("prepare_productCategoryJoinM2mEntity_basic - i: [{}], productForCategorySize: [{}], productM2mEntityListForCategory.size: [{}]"
                    , i, productForCategorySize, productM2mEntityListForCategory.size());
        }
        List<CategoryM2mEntity> categoryM2mEntityList = this.categoryM2mRepository.saveAllAndFlush(categoryM2mEntityListWip);
        log.info("prepare_productCategoryJoinM2mEntity_basic - categoryAllSize: [{}], categoryM2mEntityList.size: [{}]"
                , categoryAllSize, categoryM2mEntityList.size());
    }

    private void prepare_tagM2mEntity_basic() {
        String tagId1 = "tag-1";
        TagM2mEntity tagM2mEntity1 = new TagM2mEntity();
        tagM2mEntity1.setTagId(tagId1);
        tagM2mEntity1.setName(tagId1);
        this.tagM2mRepository.saveAndFlush(tagM2mEntity1);
    }

    @Test
    public void test_entityM2mRelation_basic() {
        log.info("test_entityM2mRelation_basic - start");

        // select pme1_0.id,pme1_0.product_id2,pme1_0.name,pme1_0.version from tbl_m2m_products pme1_0
        List<ProductM2mEntity> productM2mEntityList = this.productM2mRepository.findAll();

        // select distinct cme1_1.id,cme1_1.category_id2,cme1_1.name,cme1_1.version
        // from tbl_m2m_products pme1_0
        // join tbl_m2m_product_category_joins cme1_0 on pme1_0.product_id2=cme1_0.product_id3
        // join tbl_m2m_categories cme1_1 on cme1_1.category_id2=cme1_0.category_id3
        List<CategoryM2mEntity> productCategoryM2mEntityList = this.productM2mRepository.findDistinctCategoryBy();

        // select cme1_0.id,cme1_0.category_id2,cme1_0.name,cme1_0.version
        // from tbl_m2m_categories cme1_0
        List<CategoryM2mEntity> categoryM2mEntityList = this.categoryM2mRepository.findAll();

        // select distinct pme1_1.id,pme1_1.product_id2,pme1_1.name,pme1_1.version
        // from tbl_m2m_categories cme1_0
        // join tbl_m2m_product_category_joins pme1_0 on cme1_0.category_id2=pme1_0.category_id3
        // join tbl_m2m_products pme1_1 on pme1_1.product_id2=pme1_0.product_id3
        List<ProductM2mEntity> categoryProductM2mEntityList = this.categoryM2mRepository.findDistinctProductBy();

        log.info("test_entityM2mRelation_basic - end");
    }

    @Test
    public void test_entityM2mFlush_basic() {
        log.info("test_entityM2mFlush_basic - start");
        List<CategoryM2mEntity> productCategoryM2mEntityList = this.productM2mRepository.findDistinctCategoryBy();

        List<TagM2mEntity> tagM2mEntityListSrc = this.tagM2mRepository.findAll();
        List<TagM2mEntity> tagM2mEntityList = new ArrayList<>();
        for (TagM2mEntity tagM2mEntity : tagM2mEntityListSrc) {
            tagM2mEntity.setName(tagM2mEntity.getName() + "-2");
            tagM2mEntityList.add(tagM2mEntity);
        }
        this.tagM2mRepository.saveAllAndFlush(tagM2mEntityList);

        log.info("test_entityM2mFlush_basic - end");
    }

}
