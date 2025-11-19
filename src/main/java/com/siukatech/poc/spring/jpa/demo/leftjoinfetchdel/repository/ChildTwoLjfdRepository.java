package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity.ChildTwoLjfdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildTwoLjfdRepository extends JpaRepository<ChildTwoLjfdEntity, Long>, JpaSpecificationExecutor<ChildTwoLjfdEntity> {
}
