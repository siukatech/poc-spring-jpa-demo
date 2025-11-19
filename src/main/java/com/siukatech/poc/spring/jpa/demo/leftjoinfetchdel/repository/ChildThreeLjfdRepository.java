package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity.ChildThreeLjfdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildThreeLjfdRepository extends JpaRepository<ChildThreeLjfdEntity, Long>, JpaSpecificationExecutor<ChildThreeLjfdEntity> {
}
