package com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SoftDelete;

import java.util.List;
import java.util.Objects;

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

    @Column
    private String sid;

    @OneToMany(mappedBy = "sid", fetch = FetchType.EAGER)
    private List<ChildOneLjodEntity> childOneLjodEntityList;
    private ChildOneLjodEntity getChildOneLjodEntityList() {
        return Objects.nonNull(childOneLjodEntityList)?childOneLjodEntityList.getFirst():null;
    }

    @OneToMany(mappedBy = "sid", fetch = FetchType.EAGER)
    private List<ChildTwoLjodEntity> childTwoLjodEntityList;
    private ChildTwoLjodEntity getChildTwoLjodEntityList() {
        return Objects.nonNull(childTwoLjodEntityList)?childTwoLjodEntityList.getFirst():null;
    }

    @OneToMany(mappedBy = "sid", fetch = FetchType.EAGER)
    private List<ChildThreeLjodEntity> childThreeLjodEntityList;
    private ChildThreeLjodEntity getChildThreeLjodEntityList() {
        return Objects.nonNull(childThreeLjodEntityList)?childThreeLjodEntityList.getFirst():null;
    }

}
