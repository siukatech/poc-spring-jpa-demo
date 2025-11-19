package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity.ChildTwoLjidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildTwoLjidRepository extends JpaRepository<ChildTwoLjidEntity, Long>, JpaSpecificationExecutor<ChildTwoLjidEntity> {
}
