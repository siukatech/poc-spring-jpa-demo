package com.siukatech.poc.spring.jpa.demo.many2many.repository;

import com.siukatech.poc.spring.jpa.demo.many2many.entity.TagM2mEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagM2mRepository extends JpaRepository<TagM2mEntity, Long>, JpaSpecificationExecutor<TagM2mEntity> {
}
