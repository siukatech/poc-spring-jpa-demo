package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity.ParentLjfdEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentLjfdRepository extends JpaRepository<ParentLjfdEntity, Long>, JpaSpecificationExecutor<ParentLjfdEntity> {

    @EntityGraph(value = "ParentLjfEntity.findAll")
    List<ParentLjfdEntity> findAll();

}
