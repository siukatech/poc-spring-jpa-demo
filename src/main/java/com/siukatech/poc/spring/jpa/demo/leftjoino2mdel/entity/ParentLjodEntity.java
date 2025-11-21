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
//@ToString(exclude = {"childOneLjfEntity", "childTwoLjfEntity", "childThreeLjfEntity"})
@ToString
@Entity
@Table(name = "tbl_ljod_parent")
@SoftDelete(columnName = "is_deleted")
@NamedEntityGraph(name = "ParentLjodEntity.findAll"
        , attributeNodes = {
        @NamedAttributeNode(value = "childOneLjodEntity")
        , @NamedAttributeNode(value = "childTwoLjodEntity")
        , @NamedAttributeNode(value = "childThreeLjodEntity")
}
)
public class ParentLjodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String name;

    @OneToOne(mappedBy = "parentLjodEntity", fetch = FetchType.EAGER)
    private ChildOneLjodEntity childOneLjodEntity;

    @OneToOne(mappedBy = "parentLjodEntity", fetch = FetchType.EAGER)
    private ChildTwoLjodEntity childTwoLjodEntity;

    @OneToOne(mappedBy = "parentLjodEntity", fetch = FetchType.EAGER)
    private ChildThreeLjodEntity childThreeLjodEntity;

}
