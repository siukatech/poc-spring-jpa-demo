package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity.ChildThreeLjidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildThreeLjidRepository extends JpaRepository<ChildThreeLjidEntity, Long>, JpaSpecificationExecutor<ChildThreeLjidEntity> {
}
