package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
//@ToString(exclude = {"parentLjfdEntity"})
////@ToString
@Entity
@Table(name = "tbl_ljfd_child_three")
//@SoftDelete(columnName = "is_deleted")
public class ChildThreeLjfdEntity extends ChildBaseLjfdEntity {

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
//    @Column(name = "parent_id", insertable = false, updatable = false)
//    private String parentId;
//
//    @ManyToOne
//    @JoinColumn(name = "parent_id", referencedColumnName = "sid")
//    private ParentLjfdEntity parentLjfdEntity;

}
