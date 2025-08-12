package com.siukatech.poc.spring.jpa.demo.one2many.repository;

import com.siukatech.poc.spring.jpa.demo.one2many.entity.TagO2mEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TagO2mRepository extends JpaRepository<TagO2mEntity, Long>, JpaSpecificationExecutor<TagO2mEntity> {
}
