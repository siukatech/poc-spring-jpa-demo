package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity.ChildOneLjfdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildOneLjfdRepository extends JpaRepository<ChildOneLjfdEntity, Long>, JpaSpecificationExecutor<ChildOneLjfdEntity> {
}
