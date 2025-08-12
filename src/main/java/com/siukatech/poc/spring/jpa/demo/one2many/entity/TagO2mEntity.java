package com.siukatech.poc.spring.jpa.demo.one2many.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@ToString
@Entity
@Table(name = "tbl_o2m_tags")
public class TagO2mEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(name = "tag_id")
    private String tagId;

    @Column
    private String name;
}
