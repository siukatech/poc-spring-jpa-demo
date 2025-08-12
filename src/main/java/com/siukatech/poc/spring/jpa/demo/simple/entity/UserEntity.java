package com.siukatech.poc.spring.jpa.demo.simple.entity;

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
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(name = "user_id")
    private String userId;

    @Column
    private String name;

}
