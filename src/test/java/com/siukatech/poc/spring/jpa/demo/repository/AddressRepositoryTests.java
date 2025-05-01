package com.siukatech.poc.spring.jpa.demo.repository;

import com.siukatech.poc.spring.jpa.demo.entity.AddressEntity;
import com.siukatech.poc.spring.jpa.demo.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.show-sql=true"
        , "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo.repository=DEBUG"

})
public class AddressRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    //
    // Reference:
    // https://www.baeldung.com/java-groupingby-collector
    // https://blog.csdn.net/winterking3/article/details/116457573
    //
    // Use List.stream().collect(Collectors.toMap(key-function, value-function)) to convert List<T> => Map<K, V)
    // Use List.stream().collect(Collectors.groupingBy(key-function)) to convert List<T> => Map<K, List<V>)
    //
    @Test
    public void test_addressEntity_basic() {
        // given
        String userIdPrefix = "user";
        String userNamePrefix = "User ";
        String addressIdPrefix = "address";
        List<Pair<Integer, List<String>>> userAddressList = List.of(
                Pair.with(1, List.of("Address Line 1", "Address Line 2"))
                , Pair.with(2, List.of("Address Line 1", "Address Line 2", "Address Line 3"))
                , Pair.with(3, List.of("Address Line 1", "Address Line 2"))
                , Pair.with(4, List.of("Address Line 1"))
        );
        userAddressList.forEach(pair1 -> {
            String userId1 = userIdPrefix + pair1.getValue0();
            String userName1 = userNamePrefix + pair1.getValue0();
            UserEntity userForm1 = new UserEntity();
            userForm1.setUserId(userId1);
            userForm1.setName(userName1);
            UserEntity userEntity1 = this.userRepository.save(userForm1);
            List<String> addressLineList = pair1.getValue1();
            List<AddressEntity> addressEntityList1 = new ArrayList<>();
            for (int j=0; j<addressLineList.size(); j++) {
                String addressLine1 = addressLineList.get(j);
                String addressId1 = addressIdPrefix + j;
                AddressEntity addressForm1 = new AddressEntity();
                addressForm1.setAddressId(addressId1);
                addressForm1.setAddressLine(addressLine1);
                addressForm1.setUserEntity(userEntity1);
                addressEntityList1.add(addressForm1);
            };
            this.addressRepository.saveAll(addressEntityList1);
        });

        // when
        List<UserEntity> userEntityList2 = this.userRepository.findAll();
        List<AddressEntity> addressEntityList2 = this.addressRepository.findAll();
        log.debug("test_addressEntity_basic - userEntityList2.size: [{}]", userEntityList2.size());
        log.debug("test_addressEntity_basic - addressEntityList2.size: [{}]", addressEntityList2.size());

//        Map<UserEntity, List<AddressEntity>> userAddressListMap1 = this.addressRepository.findAllGroupByUserEntity();
//        log.debug("test_addressEntity_basic - userAddressListMap1.size: [{}]", userAddressListMap1.size());

        List<Long> userIdList2 = userEntityList2.stream().map(UserEntity::getId).toList();
        List<AddressEntity> addressEntityList3 = this.addressRepository
                .findAllByUserEntityIdInOrderByUserEntityIdAsc(userIdList2);
        log.debug("test_addressEntity_basic - addressEntityList3.size: [{}]", addressEntityList3.size());

        Map<UserEntity, List<AddressEntity>> userEntityAddressEntityListMap1 = addressEntityList3.stream()
                .collect(Collectors.groupingBy(AddressEntity::getUserEntity));
        log.debug("test_addressEntity_basic - userEntityAddressEntityListMap1: [{}]", userEntityAddressEntityListMap1);
        userEntityAddressEntityListMap1.forEach((key, value) -> {
            log.debug("test_addressEntity_basic - userEntityAddressEntityListMap1 - key.getId: [{}], key.getUserId: [{}], value.size: [{}]"
                    , key.getId(), key.getUserId(), value.size());
            value.forEach(addressEntity -> {
                log.debug("test_addressEntity_basic - userEntityAddressEntityListMap1 - key.getId: [{}], key.getUserId: [{}]"
                                + ", addressEntity.getAddressId: [{}], addressEntity.getAddressLine: [{}]"
                        , key.getId(), key.getUserId(), addressEntity.getAddressId(), addressEntity.getAddressLine());
            });
        });

        Map<UserEntity, Set<AddressEntity>> userEntityAddressEntitySetMap2 = addressEntityList3.stream()
                .collect(Collectors.groupingBy(AddressEntity::getUserEntity, Collectors.toSet()));

        // then

    }

}
