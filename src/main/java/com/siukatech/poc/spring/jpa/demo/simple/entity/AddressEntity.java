package com.siukatech.poc.spring.jpa.demo.simple.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SoftDelete;

@Slf4j
@Setter
@Getter
@ToString
@Entity
@SoftDelete(columnName = "is_deleted")
@Table(name = "tbl_addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Version
    @Column
    private Long version;

    @ManyToOne
    @JoinColumn(name = "user_id2", referencedColumnName = "user_id")
    private UserEntity userEntity;

    @Column(name = "user_id2", insertable = false, updatable = false)
    private String userId;

    @Column(name = "address_id")
    private String addressId;

    @Column
    private String addressLine;

}
