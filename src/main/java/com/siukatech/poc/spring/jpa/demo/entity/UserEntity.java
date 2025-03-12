package com.siukatech.poc.spring.jpa.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
@Entity
@Table(name = "tbl_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column
    private String name;

    @Version
    @Column
    private Long version;

}
