package com.siukatech.poc.spring.jpa.demo.many2many.service;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.TagM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.helper.EntityM2mHelper;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.CategoryM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.ProductM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.TagM2mRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith({MockitoExtension.class
        , SpringExtension.class
})
@TestPropertySource(properties = {
        "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
})
public class ProductM2mServiceTests {

    @Mock
    private ProductM2mRepository productM2mRepository;

    @Mock
    private CategoryM2mRepository categoryM2mRepository;

    @Mock
    private TagM2mRepository tagM2mRepository;

    @InjectMocks
    private ProductM2mService productM2mService;

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
    public void test_performAction_basic() {
        this.productM2mService.performAction();
    }

}
