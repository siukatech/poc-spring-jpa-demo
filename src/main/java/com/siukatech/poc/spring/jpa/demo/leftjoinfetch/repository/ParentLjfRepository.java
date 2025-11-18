package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ParentLjfEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentLjfRepository extends JpaRepository<ParentLjfEntity, Long>, JpaSpecificationExecutor<ParentLjfEntity> {

    @EntityGraph(value = "ParentLjfEntity.findAll")
    List<ParentLjfEntity> findAll();

}
