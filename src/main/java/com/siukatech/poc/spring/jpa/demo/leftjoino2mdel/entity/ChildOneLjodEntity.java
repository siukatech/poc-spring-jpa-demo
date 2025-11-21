package com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Entity
@Table(name = "tbl_ljod_child_one")
public class ChildOneLjodEntity extends ChildBaseLjodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

}
