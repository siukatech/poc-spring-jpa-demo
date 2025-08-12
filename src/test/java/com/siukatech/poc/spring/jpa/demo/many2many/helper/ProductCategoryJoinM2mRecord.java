package com.siukatech.poc.spring.jpa.demo.many2many.helper;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;

import java.util.List;

public record ProductCategoryJoinM2mRecord(
        List<ProductM2mEntity> productM2mEntityListWip
        , List<CategoryM2mEntity> categoryM2mEntityListWip
) {
}
