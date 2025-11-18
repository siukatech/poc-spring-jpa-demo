package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
//@ToString(exclude = {"childOneLjfEntity", "childTwoLjfEntity", "childThreeLjfEntity"})
@ToString
@Entity
@Table(name = "tbl_ljf_parent")
@NamedEntityGraph(name = "ParentLjfEntity.findAll"
        , attributeNodes = {
        @NamedAttributeNode(value = "childOneLjfEntity")
        , @NamedAttributeNode(value = "childTwoLjfEntity")
        , @NamedAttributeNode(value = "childThreeLjfEntity")
}
)
public class ParentLjfEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @Column
    private String sid;

    @OneToOne(mappedBy = "parentLjfEntity", fetch = FetchType.EAGER)
    private ChildOneLjfEntity childOneLjfEntity;

    @OneToOne(mappedBy = "parentLjfEntity", fetch = FetchType.EAGER)
    private ChildTwoLjfEntity childTwoLjfEntity;

    @OneToOne(mappedBy = "parentLjfEntity", fetch = FetchType.EAGER)
    private ChildThreeLjfEntity childThreeLjfEntity;

}
