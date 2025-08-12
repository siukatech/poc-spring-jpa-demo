package com.siukatech.poc.spring.jpa.demo.simple.repository;

import com.siukatech.poc.spring.jpa.demo.simple.entity.AddressEntity;
import com.siukatech.poc.spring.jpa.demo.simple.entity.UserEntity;
import com.siukatech.poc.spring.jpa.demo.simple.repository.AddressRepository;
import com.siukatech.poc.spring.jpa.demo.simple.repository.UserRepository;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.properties.hibernate.show-sql=true"
        , "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"

})
public class AddressRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_testData_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    public void prepare_testData_basic() {
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
            for (int j = 0; j < addressLineList.size(); j++) {
                String addressLine1 = addressLineList.get(j);
                String addressId1 = addressIdPrefix + j;
                AddressEntity addressForm1 = new AddressEntity();
                addressForm1.setAddressId(addressId1);
                addressForm1.setAddressLine(addressLine1);
                addressForm1.setUserEntity(userEntity1);
                addressEntityList1.add(addressForm1);
            }
            ;
            this.addressRepository.saveAll(addressEntityList1);
        });
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
//        String userIdPrefix = "user";
//        String userNamePrefix = "User ";
//        String addressIdPrefix = "address";
//        List<Pair<Integer, List<String>>> userAddressList = List.of(
//                Pair.with(1, List.of("Address Line 1", "Address Line 2"))
//                , Pair.with(2, List.of("Address Line 1", "Address Line 2", "Address Line 3"))
//                , Pair.with(3, List.of("Address Line 1", "Address Line 2"))
//                , Pair.with(4, List.of("Address Line 1"))
//        );
//        userAddressList.forEach(pair1 -> {
//            String userId1 = userIdPrefix + pair1.getValue0();
//            String userName1 = userNamePrefix + pair1.getValue0();
//            UserEntity userForm1 = new UserEntity();
//            userForm1.setUserId(userId1);
//            userForm1.setName(userName1);
//            UserEntity userEntity1 = this.userRepository.save(userForm1);
//            List<String> addressLineList = pair1.getValue1();
//            List<AddressEntity> addressEntityList1 = new ArrayList<>();
//            for (int j=0; j<addressLineList.size(); j++) {
//                String addressLine1 = addressLineList.get(j);
//                String addressId1 = addressIdPrefix + j;
//                AddressEntity addressForm1 = new AddressEntity();
//                addressForm1.setAddressId(addressId1);
//                addressForm1.setAddressLine(addressLine1);
//                addressForm1.setUserEntity(userEntity1);
//                addressEntityList1.add(addressForm1);
//            };
//            this.addressRepository.saveAll(addressEntityList1);
//        });

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

    public Specification<UserEntity> getUserSpec(String addressLine) {
        Specification<UserEntity> userSpec = ((root
                , query, cb) -> {
            Assertions.assertNotNull(query);
//            Subquery<Long> subquery = query.subquery(Long.class);
            Subquery<String> subquery = query.subquery(String.class);
//            Subquery<AddressEntity> subquery = query.subquery(AddressEntity.class);
            Root<AddressEntity> subRoot = subquery.from(AddressEntity.class);
            subquery.select(subRoot.get("userEntity").get("userId"))
                    .where(
                            cb.equal(subRoot.get("userEntity").get("userId"), root.get("userId"))
                            , cb.like(
                                    cb.upper(subRoot.get("addressLine"))
                                    , "%" + addressLine.toUpperCase() + "%"
                            )
                    );
            //
            Predicate predicate = null;
            //
            // select ue1_0.id,ue1_0.name,ue1_0.user_id,ue1_0.version
            // from tbl_users ue1_0 where exists(
            //   select ae1_0.user_id from tbl_addresses ae1_0
            //   where ae1_0.user_id=ue1_0.user_id and upper(ae1_0.address_line) like ?
            //   escape ''
            // )
//            predicate = cb.exists(subquery);
            //
            // select ue1_0.id,ue1_0.name,ue1_0.user_id,ue1_0.version
            // from tbl_users ue1_0 where ue1_0.user_id in ((
            //   select ae1_0.user_id from tbl_addresses ae1_0
            //   where ae1_0.user_id=ue1_0.user_id and upper(ae1_0.address_line) like ?
            //   escape ''
            // ))
            predicate = cb.in(root.get("userId")).value(subquery);
            //
            // *** Incorrect
            // select ue1_0.id,ue1_0.name,ue1_0.user_id,ue1_0.version from tbl_users ue1_0 where 1=0
//            predicate = cb.in(subquery);
            //
//            log.debug("getUserSpec - predicate: [{}]", predicate);
            return predicate;
        });
        return userSpec;
    }

    @Test
    public void test_userSpec_basic() {
        // given


        // when
        Specification<UserEntity> userSpec = this.getUserSpec("line 2");
        List<UserEntity> userEntityList = this.userRepository.findAll(userSpec);
        log.debug("test_userSpec_basic - userEntityList.size: [{}]", userEntityList.size());

        // then
        assertThat(userEntityList.size()).isEqualTo(3);

    }

    @Test
    public void test_addressSpec_basic() {
        // given
        BiFunction<String, String, Specification<AddressEntity>> getAddressSpec = (String userName
                , String addressLine) -> {
            Specification<AddressEntity> addressSpec = ((root
                    , query, cb) -> {
                Assertions.assertNotNull(query);
                Subquery<String> subquery = query.subquery(String.class);
                Root<UserEntity> subRoot = subquery.from(UserEntity.class);
                subquery.select(subRoot.get("userId"))
                        .where(
                                cb.equal(subRoot.get("userId"), root.get("userEntity").get("userId"))
                                , cb.like(
                                        cb.upper(subRoot.get("name"))
                                        , "%" + userName.toUpperCase() + "%"
                                )
                        );
                //
                Predicate predicate = null;
                //
                // select ae1_0.id,ae1_0.address_id,ae1_0.address_line,ae1_0.user_id,ae1_0.version
                // from tbl_addresses ae1_0 where ae1_0.user_id in ((
                //   select ue2_0.user_id from tbl_users ue2_0
                //   where ue2_0.user_id=ae1_0.user_id and upper(ue2_0.name) like ?
                //   escape ''
                // ))
                // and upper(ae1_0.address_line) like ? escape ''
                predicate = cb.and(
                        cb.in(root.get("userEntity").get("userId")).value(subquery)
                        , cb.like(
                                cb.upper(root.get("addressLine"))
                                , "%" + addressLine.toUpperCase() + "%"
                        )
                );
                return predicate;
            });
            return addressSpec;
        };

        // when
        Specification<AddressEntity> addressSpec = getAddressSpec.apply("User ", "line 2");
        List<AddressEntity> addressEntityList = this.addressRepository.findAll(addressSpec);
        log.debug("test_addressSpec_basic - addressEntityList.size: [{}]", addressEntityList.size());

        // then
        assertThat(addressEntityList.size()).isEqualTo(3);

    }

    @Test
    public void test_addressSpec_delete_basic() {
        // given
        List<UserEntity> userEntityList = this.userRepository.findAll();
        log.debug("test_addressSpec_delete_basic - userEntityList: [{}]", userEntityList);
        userEntityList.forEach(userEntity -> {
            log.debug("test_addressSpec_delete_basic - userEntity: [{}]", userEntity);
        });
        List<AddressEntity> addressEntityList = this.addressRepository.findAll();
        log.debug("test_addressSpec_delete_basic - userEntityList: [{}]", addressEntityList);
        addressEntityList.forEach(addressEntity -> {
            log.debug("test_addressSpec_delete_basic - addressEntity: [{}]", addressEntity);
        });
        Long userId = userEntityList.getFirst().getId();
        Long addressId = addressEntityList.getLast().getId();
        log.debug("test_addressSpec_delete_basic - userId: [{}], addressId: [{}]", userId, addressId);
        UserEntity userEntity = this.userRepository.findById(userId).orElseThrow();
        AddressEntity addressEntity = this.addressRepository.findById(addressId).orElseThrow();
        Specification<AddressEntity> addressSpec = ((root, query, cb) -> {
            Predicate predicate = null;
            Path<String> path = null;
//            path = root.get("userEntity").get("userId");
//            predicate = cb.equal(path, userEntity.getUserId());
            path = root.get("userId");
            predicate = cb.equal(path, userEntity.getUserId());
            return predicate;
        });

        // when
        addressEntity.setAddressLine(addressEntity.getAddressLine() + "_updated");
        Long deleted = this.addressRepository.delete(addressSpec);
        log.debug("test_addressSpec_delete_basic - deleted: [{}]", deleted);

        // then
        assertThat(deleted).isGreaterThan(0L);
    }

}
