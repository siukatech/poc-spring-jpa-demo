package com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.entity;

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
@ToString(exclude = {"parentLjodEntity"})
@SoftDelete(columnName = "is_deleted")
public abstract class ChildBaseLjodEntity {

    public abstract Long getId();

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @Column(name = "parent_sid", insertable = false, updatable = false)
    private String parentSid;

    @ManyToOne
    @JoinColumn(name = "parent_sid", referencedColumnName = "sid")
    private ParentLjodEntity parentLjodEntity;

}
