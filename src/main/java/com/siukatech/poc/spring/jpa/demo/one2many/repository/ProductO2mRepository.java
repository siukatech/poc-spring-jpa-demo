package com.siukatech.poc.spring.jpa.demo.one2many.repository;

import com.siukatech.poc.spring.jpa.demo.one2many.entity.CategoryO2mEntity;
import com.siukatech.poc.spring.jpa.demo.one2many.entity.ProductO2mEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductO2mRepository extends JpaRepository<ProductO2mEntity, Long>, JpaSpecificationExecutor<ProductO2mEntity> {

    @Query(value = """
    select distinct c
    from ProductO2mEntity p
    join p.productCategoryJoinO2mEntities j
    join j.categoryO2mEntity c
    """)
    List<CategoryO2mEntity> findDistinctCategoryBy();

}
