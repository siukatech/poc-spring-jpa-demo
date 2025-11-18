package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ChildThreeLjfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildThreeLjfRepository extends JpaRepository<ChildThreeLjfEntity, Long>, JpaSpecificationExecutor<ChildThreeLjfEntity> {
}
