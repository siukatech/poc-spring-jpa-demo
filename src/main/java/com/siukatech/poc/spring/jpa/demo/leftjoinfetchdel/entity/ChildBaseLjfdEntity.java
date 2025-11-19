package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity;

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
@ToString(exclude = {"parentLjfdEntity"})
@SoftDelete(columnName = "is_deleted")
//@Entity
public abstract class ChildBaseLjfdEntity {
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

    @Column(name = "parent_sid", insertable = false, updatable = false)
    private String parentSid;

    @ManyToOne
    @JoinColumn(name = "parent_sid", referencedColumnName = "sid")
    private ParentLjfdEntity parentLjfdEntity;

}
