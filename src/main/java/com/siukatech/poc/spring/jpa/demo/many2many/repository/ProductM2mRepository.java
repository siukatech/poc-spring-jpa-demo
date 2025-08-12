package com.siukatech.poc.spring.jpa.demo.many2many.repository;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductM2mRepository extends JpaRepository<ProductM2mEntity, Long>, JpaSpecificationExecutor<ProductM2mEntity> {

    @Query(value = """
    select distinct c
    from ProductM2mEntity p
    join p.categoryM2mEntities c
    """)
    List<CategoryM2mEntity> findDistinctCategoryBy();

}
