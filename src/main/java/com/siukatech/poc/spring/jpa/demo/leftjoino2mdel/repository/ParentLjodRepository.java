//package com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.repository;
//
//import com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.entity.ParentLjodEntity;
//import org.springframework.data.jpa.repository.EntityGraph;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ParentLjodRepository extends JpaRepository<ParentLjodEntity, Long>, JpaSpecificationExecutor<ParentLjodEntity> {
//
//    @EntityGraph(value = "ParentLjodEntity.findAll")
//    List<ParentLjodEntity> findAll();
//
//}
