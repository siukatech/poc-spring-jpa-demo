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
//@ToString(exclude = {"childOneLjfEntity", "childTwoLjfEntity", "childThreeLjfEntity"})
@ToString
@Entity
@Table(name = "tbl_ljfd_parent")
@SoftDelete(columnName = "is_deleted")
@NamedEntityGraph(name = "ParentLjfdEntity.findAll"
        , attributeNodes = {
        @NamedAttributeNode(value = "childOneLjfdEntity")
        , @NamedAttributeNode(value = "childTwoLjfdEntity")
        , @NamedAttributeNode(value = "childThreeLjfdEntity")
}
)
public class ParentLjfdEntity {

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

    @OneToOne(mappedBy = "parentLjfdEntity", fetch = FetchType.EAGER)
    private ChildOneLjfdEntity childOneLjfdEntity;

    @OneToOne(mappedBy = "parentLjfdEntity", fetch = FetchType.EAGER)
    private ChildTwoLjfdEntity childTwoLjfdEntity;

    @OneToOne(mappedBy = "parentLjfdEntity", fetch = FetchType.EAGER)
    private ChildThreeLjfdEntity childThreeLjfdEntity;

}
