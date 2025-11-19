package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Entity
@Table(name = "tbl_ljid_child_two")
public class ChildTwoLjidEntity extends ChildBaseLjidEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

}
