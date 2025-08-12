package com.siukatech.poc.spring.jpa.demo.many2many.entity;

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
@Table(name = "tbl_m2m_tags")
public class TagM2mEntity {

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
