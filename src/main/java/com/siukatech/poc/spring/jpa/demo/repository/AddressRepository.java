package com.siukatech.poc.spring.jpa.demo.repository;

import com.siukatech.poc.spring.jpa.demo.entity.AddressEntity;
import com.siukatech.poc.spring.jpa.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long>, JpaSpecificationExecutor<AddressEntity> {

//    @Query(value = "select o.userEntity, o from AddressEntity o where 1=1 group by o.userEntity, o.id")
//    Map<UserEntity, List<AddressEntity>> findAllGroupByUserEntity();

    List<AddressEntity> findAllByUserEntityIdInOrderByUserEntityIdAsc(List<Long> ids);

}
