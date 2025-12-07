//package com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.repository;
//
//import com.siukatech.poc.spring.jpa.demo.leftjoino2mdel.entity.*;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Predicate;
//import jakarta.persistence.criteria.Root;
//import lombok.extern.slf4j.Slf4j;
//import org.javatuples.Pair;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.test.context.TestPropertySource;
//
//import java.util.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@Slf4j
//@DataJpaTest
//@TestPropertySource(properties = {
//        "logging.level.org.hibernate.SQL=DEBUG"
//        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
//        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
////        , "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
//        , "spring.jpa.properties.hibernate.ddl-auto=none"
//        , "spring.jpa.properties.hibernate.show-sql=true"
//        , "spring.jpa.properties.hibernate.format-sql=true"
//})
//public class ParentLjodRepositoryTests {
//
//    @Autowired
//    private EntityManager entityManager;
//
//    @Autowired
//    private ParentLjodRepository parentLjodRepository;
//
//    @Autowired
//    private ChildOneLjodRepository childOneLjodRepository;
//
//    @Autowired
//    private ChildTwoLjodRepository childTwoLjodRepository;
//
//    @Autowired
//    private ChildThreeLjodRepository childThreeLjodRepository;
//
//    private final Random random = new Random();
//
//    @BeforeEach
//    public void setup(TestInfo testInfo) {
//        log.info("setup - testInfo: [{}]", testInfo);
//        //
//        this.prepare_parentLjodEntity_basic();
//    }
//
//    @AfterEach
//    public void teardown(TestInfo testInfo) {
//        log.info("teardown - testInfo: [{}]", testInfo);
//    }
//
//    private void prepare_parentLjodEntity_basic() {
//        int recSize = 10;
//        List<Pair<ParentLjodEntity, ChildBaseLjodEntity>> pairList = new ArrayList<>();
//        for (int i=0; i<recSize; i++) {
//            int s = i + 1;
//            String sid = "sid-%s".formatted(s);
//            ParentLjodEntity parentLjodEntityDraft = new ParentLjodEntity();
//            parentLjodEntityDraft.setName(sid);
//            ParentLjodEntity parentLjodEntitySaved = this.parentLjodRepository.save(parentLjodEntityDraft);
//            log.info("prepare_parentLjodEntity_basic - parentLjodEntitySaved: [{}]", parentLjodEntitySaved);
//            ChildBaseLjodEntity childBaseLjodEntitySaved;
//            if (s % 2 == 0) {
//                ChildTwoLjodEntity childTwoLjodEntity2 = new ChildTwoLjodEntity();
//                childTwoLjodEntity2.setName(sid);
//                childTwoLjodEntity2.setParentLjodEntity(parentLjodEntitySaved);
//                childBaseLjodEntitySaved = this.childTwoLjodRepository.save(childTwoLjodEntity2);
//            }
//            else if (s % 3 == 0) {
//                ChildThreeLjodEntity childThreeLjodEntity3 = new ChildThreeLjodEntity();
//                childThreeLjodEntity3.setName(sid);
//                childThreeLjodEntity3.setParentLjodEntity(parentLjodEntitySaved);
//                childBaseLjodEntitySaved = this.childThreeLjodRepository.save(childThreeLjodEntity3);
//            }
//            else {
//                ChildOneLjodEntity childOneLjodEntity1 = new ChildOneLjodEntity();
//                childOneLjodEntity1.setName(sid);
//                childOneLjodEntity1.setParentLjodEntity(parentLjodEntitySaved);
//                childBaseLjodEntitySaved = this.childOneLjodRepository.save(childOneLjodEntity1);
//            }
//            pairList.add(Pair.with(parentLjodEntitySaved, childBaseLjodEntitySaved));
//        }
//        this.entityManager.flush();
//        this.entityManager.clear();
//        //
//        int delSize = random.nextInt(1, Math.round(((float) pairList.size() / 3)));
//        Set<Pair<ParentLjodEntity, ChildBaseLjodEntity>> delPairSet = new HashSet<>();
//        for (int i=0; i<delSize; i++) {
//            int delIndex = random.nextInt(0, pairList.size());
//            Pair<ParentLjodEntity, ChildBaseLjodEntity> delPair = pairList.get(delIndex);
//            delPairSet.add(delPair);
//        }
//        delPairSet.forEach(delPair -> {
//            this.childOneLjodRepository.delete(new Specification<ChildOneLjodEntity>() {
//                @Override
//                public Predicate toPredicate(Root<ChildOneLjodEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    log.info("prepare_parentLjodEntity_basic - childOneLjodRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
//                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
//                }
//            });
//            this.childTwoLjodRepository.delete(new Specification<ChildTwoLjodEntity>() {
//                @Override
//                public Predicate toPredicate(Root<ChildTwoLjodEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    log.info("prepare_parentLjodEntity_basic - childTwoLjodRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
//                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
//                }
//            });
//            this.childThreeLjodRepository.delete(new Specification<ChildThreeLjodEntity>() {
//                @Override
//                public Predicate toPredicate(Root<ChildThreeLjodEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                    log.info("prepare_parentLjodEntity_basic - childThreeLjodRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
//                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
//                }
//            });
//            this.parentLjodRepository.deleteById(delPair.getValue0().getId());
//        });
//        this.entityManager.flush();
//        this.entityManager.clear();
//    }
//
////    @Test
//    public void test_leftJoinO2mDel_basic() {
//        log.info("test_leftJoinO2mDel_basic - start");
//
////        JpaObjectRetrievalFailureException exception = assertThrows(JpaObjectRetrievalFailureException.class, () -> {
//        Exception exception = assertThrows(Exception.class, () -> {
//
//        // select ple1_0.id,ple1_0.sid,ple1_0.name,ple1_0.version from tbl_ljf_parent ple1_0
//        List<ParentLjodEntity> parentLjodEntityList = this.parentLjodRepository.findAll();
//        log.info("test_leftJoinIdDel_basic - parentLjodEntityList.size: [{}]", parentLjodEntityList.size());
//        parentLjodEntityList.forEach(e -> {
//            log.info("test_leftJoinIdDel_basic - parentLjodEntityList - e: [{}]", e.toString());
//        });
//
//        });
//        assertThat(exception.getMessage()).contains("with identifier value");
//
//        log.info("test_leftJoinIdDel_basic - end");
//    }
//
//}
