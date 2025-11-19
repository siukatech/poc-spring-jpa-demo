package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity.ParentLjidEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentLjidRepository extends JpaRepository<ParentLjidEntity, Long>, JpaSpecificationExecutor<ParentLjidEntity> {

    @EntityGraph(value = "ParentLjidEntity.findAll")
    List<ParentLjidEntity> findAll();

}
