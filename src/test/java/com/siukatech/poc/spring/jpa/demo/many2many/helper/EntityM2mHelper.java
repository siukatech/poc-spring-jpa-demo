package com.siukatech.poc.spring.jpa.demo.many2many.helper;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.TagM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.CategoryM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.ProductM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.TagM2mRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class EntityM2mHelper {

//    public void prepare_productCategoryJoinM2mEntity_basic() {
//        List<ProductM2mEntity> productM2mEntityListWip = new ArrayList<>();
//        int productAllSize = (int) (Math.random() * 6);
//        for (int i=0; i<productAllSize; i++) {
//            String productId1 = "product-%s".formatted(i);
//            ProductM2mEntity productM2mEntity1 = new ProductM2mEntity();
//            productM2mEntity1.setProductId(productId1);
//            productM2mEntity1.setName(productId1);
//            productM2mEntity1.setCategoryM2mEntities(new ArrayList<>());
//            productM2mEntityListWip.add(productM2mEntity1);
//        }
//        List<ProductM2mEntity> productM2mEntityList = this.productM2mRepository.saveAllAndFlush(productM2mEntityListWip);
//        log.info("prepare_productCategoryJoinM2mEntity_basic - productAllSize: [{}], productM2mEntityList.size: [{}]"
//                , productAllSize, productM2mEntityList.size());
//        //
//        List<CategoryM2mEntity> categoryM2mEntityListWip = new ArrayList<>();
//        int categoryAllSize = (int) (Math.random() * 6);
//        for (int i=0; i<categoryAllSize; i++) {
////            int productForCategorySize = (int) (Math.random() * productM2mEntityList.size());
//            int productForCategorySize = productAllSize;
//            List<ProductM2mEntity> productM2mEntityListForCategory = new ArrayList<>();
//            for (int j=0; j<productForCategorySize; j++) {
//                productM2mEntityListForCategory.add(productM2mEntityList.get(j));
//            }
//            String categoryId1 = "category-%s".formatted(i);
//            CategoryM2mEntity categoryM2mEntity1 = new CategoryM2mEntity();
//            categoryM2mEntity1.setCategoryId(categoryId1);
//            categoryM2mEntity1.setName(categoryId1);
//            categoryM2mEntity1.setProductM2mEntities(productM2mEntityListForCategory);
//            categoryM2mEntityListWip.add(categoryM2mEntity1);
//            log.info("prepare_productCategoryJoinM2mEntity_basic - i: [{}], productForCategorySize: [{}], productM2mEntityListForCategory.size: [{}]"
//                    , i, productForCategorySize, productM2mEntityListForCategory.size());
//        }
//        List<CategoryM2mEntity> categoryM2mEntityList = this.categoryM2mRepository.saveAllAndFlush(categoryM2mEntityListWip);
//        log.info("prepare_productCategoryJoinM2mEntity_basic - categoryAllSize: [{}], categoryM2mEntityList.size: [{}]"
//                , categoryAllSize, categoryM2mEntityList.size());
//    }

    public List<ProductM2mEntity> prepare_productM2mEntityList_basic() {
        List<ProductM2mEntity> productM2mEntityListWip = new ArrayList<>();
        int productAllSize = (int) (Math.random() * 6);
        for (int i = 0; i < productAllSize; i++) {
            String productId1 = "product-%s".formatted(i);
            ProductM2mEntity productM2mEntity1 = new ProductM2mEntity();
            productM2mEntity1.setProductId(productId1);
            productM2mEntity1.setName(productId1);
            productM2mEntity1.setCategoryM2mEntities(new ArrayList<>());
            productM2mEntityListWip.add(productM2mEntity1);
        }
        return productM2mEntityListWip;
    }

    public List<CategoryM2mEntity> prepare_categoryM2mEntityList_basic(
            List<ProductM2mEntity> productM2mEntityList) {
        int productAllSize = productM2mEntityList.size();
        log.info("prepare_categoryM2mEntityList_basic - productAllSize: [{}], productM2mEntityList.size: [{}]"
                , productAllSize, productM2mEntityList.size());
        //
        List<CategoryM2mEntity> categoryM2mEntityListWip = new ArrayList<>();
        int categoryAllSize = (int) (Math.random() * 6);
        for (int i=0; i<categoryAllSize; i++) {
//            int productForCategorySize = (int) (Math.random() * productM2mEntityList.size());
            int productForCategorySize = productAllSize;
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
            log.info("prepare_categoryM2mEntityList_basic - i: [{}], productForCategorySize: [{}], productM2mEntityListForCategory.size: [{}]"
                    , i, productForCategorySize, productM2mEntityListForCategory.size());
        }
//        List<CategoryM2mEntity> categoryM2mEntityList = this.categoryM2mRepository.saveAllAndFlush(categoryM2mEntityListWip);
//        log.info("prepare_productCategoryJoinM2mEntity_basic - categoryAllSize: [{}], categoryM2mEntityList.size: [{}]"
//                , categoryAllSize, categoryM2mEntityList.size());
        return categoryM2mEntityListWip;
    }

    public TagM2mEntity prepare_tagM2mEntity_basic() {
        String tagId1 = "tag-1";
        TagM2mEntity tagM2mEntity1 = new TagM2mEntity();
        tagM2mEntity1.setTagId(tagId1);
        tagM2mEntity1.setName(tagId1);
//        this.tagM2mRepository.saveAndFlush(tagM2mEntity1);
        return tagM2mEntity1;
    }

}
