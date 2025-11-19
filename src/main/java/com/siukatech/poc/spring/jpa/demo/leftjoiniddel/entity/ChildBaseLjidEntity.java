package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SoftDelete;

@Slf4j
@Setter
@Getter
@MappedSuperclass
@ToString(exclude = {"parentLjidEntity"})
@SoftDelete(columnName = "is_deleted")
public abstract class ChildBaseLjidEntity {

    public abstract Long getId();

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private ParentLjidEntity parentLjidEntity;

}
