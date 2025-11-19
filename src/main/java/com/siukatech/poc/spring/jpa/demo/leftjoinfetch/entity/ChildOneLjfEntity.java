package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
//@ToString(exclude = {"parentLjfEntity"})
////@ToString
@Entity
@Table(name = "tbl_ljf_child_one")
public class ChildOneLjfEntity extends ChildBaseLjfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

//    @Version
//    @Column
//    private Long version;
//
//    @Column
//    private String name;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_id", referencedColumnName = "sid")
//    private ParentLjfEntity parentLjfEntity;

}
