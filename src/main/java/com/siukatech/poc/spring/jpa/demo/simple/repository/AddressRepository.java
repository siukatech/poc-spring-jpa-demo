package com.siukatech.poc.spring.jpa.demo.simple.repository;

import com.siukatech.poc.spring.jpa.demo.simple.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {

//    @Query(value = "select o.userEntity, o from AddressEntity o where 1=1 group by o.userEntity, o.id")
//    Map<UserEntity, List<AddressEntity>> findAllGroupByUserEntity();

    List<AddressEntity> findAllByUserEntityIdInOrderByUserEntityIdAsc(List<Long> ids);

}
