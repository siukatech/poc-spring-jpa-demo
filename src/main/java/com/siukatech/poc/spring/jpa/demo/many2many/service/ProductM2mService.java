package com.siukatech.poc.spring.jpa.demo.many2many.service;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.TagM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.CategoryM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.ProductM2mRepository;
import com.siukatech.poc.spring.jpa.demo.many2many.repository.TagM2mRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProductM2mService {

    private final ProductM2mRepository productM2mRepository;
    private final CategoryM2mRepository categoryM2mRepository;
    private final TagM2mRepository tagM2mRepository;

    public ProductM2mService(ProductM2mRepository productM2mRepository
            , CategoryM2mRepository categoryM2mRepository
            , TagM2mRepository tagM2mRepository) {
        this.productM2mRepository = productM2mRepository;
        this.categoryM2mRepository = categoryM2mRepository;
        this.tagM2mRepository = tagM2mRepository;
    }

    @Transactional
    public void performAction() {
        log.info("performAction - start");

        List<CategoryM2mEntity> productCategoryM2mEntityList = this.productM2mRepository.findDistinctCategoryBy();

        List<TagM2mEntity> tagM2mEntityListSrc = this.tagM2mRepository.findAll();
        List<TagM2mEntity> tagM2mEntityList = new ArrayList<>();
        for (TagM2mEntity tagM2mEntity : tagM2mEntityListSrc) {
            tagM2mEntity.setName(tagM2mEntity.getName() + "-2");
            tagM2mEntityList.add(tagM2mEntity);
        }
        this.tagM2mRepository.saveAllAndFlush(tagM2mEntityList);

        log.info("performAction - end");
    }

}
