package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SoftDelete;

@Slf4j
@Setter
@Getter
@ToString(exclude = {"parentLjfEntity"})
//@Entity
@MappedSuperclass
public abstract class ChildBaseLjfEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;

    public abstract Long getId();

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private String parentId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "sid")
    private ParentLjfEntity parentLjfEntity;

}
