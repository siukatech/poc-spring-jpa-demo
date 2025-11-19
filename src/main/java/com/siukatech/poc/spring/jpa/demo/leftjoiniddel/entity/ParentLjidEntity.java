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
//@ToString(exclude = {"childOneLjfEntity", "childTwoLjfEntity", "childThreeLjfEntity"})
@ToString
@Entity
@Table(name = "tbl_ljid_parent")
@SoftDelete(columnName = "is_deleted")
@NamedEntityGraph(name = "ParentLjidEntity.findAll"
        , attributeNodes = {
        @NamedAttributeNode(value = "childOneLjidEntity")
        , @NamedAttributeNode(value = "childTwoLjidEntity")
        , @NamedAttributeNode(value = "childThreeLjidEntity")
}
)
public class ParentLjidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @OneToOne(mappedBy = "parentLjidEntity", fetch = FetchType.EAGER)
    private ChildOneLjidEntity childOneLjidEntity;

    @OneToOne(mappedBy = "parentLjidEntity", fetch = FetchType.EAGER)
    private ChildTwoLjidEntity childTwoLjidEntity;

    @OneToOne(mappedBy = "parentLjidEntity", fetch = FetchType.EAGER)
    private ChildThreeLjidEntity childThreeLjidEntity;

}
