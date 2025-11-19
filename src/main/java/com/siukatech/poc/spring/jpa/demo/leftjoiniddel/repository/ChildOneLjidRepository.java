package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity.ChildOneLjidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildOneLjidRepository extends JpaRepository<ChildOneLjidEntity, Long>, JpaSpecificationExecutor<ChildOneLjidEntity> {
}
