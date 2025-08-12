package com.siukatech.poc.spring.jpa.demo.many2many.repository;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.CategoryM2mEntity;
import com.siukatech.poc.spring.jpa.demo.many2many.entity.ProductM2mEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryM2mRepository extends JpaRepository<CategoryM2mEntity, Long>, JpaSpecificationExecutor<CategoryM2mEntity> {

    @Query(value = """
    select distinct p
    from CategoryM2mEntity c
    join c.productM2mEntities p
    """)
    List<ProductM2mEntity> findDistinctProductBy();

}
