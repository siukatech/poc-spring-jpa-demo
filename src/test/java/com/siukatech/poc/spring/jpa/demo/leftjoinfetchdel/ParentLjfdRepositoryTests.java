package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.entity.*;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildOneLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildThreeLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildTwoLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ParentLjfdRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
@TestPropertySource(properties = {
        "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
//        , "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect"
        , "spring.jpa.properties.hibernate.ddl-auto=none"
        , "spring.jpa.properties.hibernate.show-sql=true"
        , "spring.jpa.properties.hibernate.format-sql=true"
})
public class ParentLjfdRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParentLjfdRepository parentLjfdRepository;

    @Autowired
    private ChildOneLjfdRepository childOneLjfdRepository;

    @Autowired
    private ChildTwoLjfdRepository childTwoLjfdRepository;

    @Autowired
    private ChildThreeLjfdRepository childThreeLjfdRepository;

    private final Random random = new Random();

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_parentLjfdEntity_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    private void prepare_parentLjfdEntity_basic() {
        int recSize = 10;
        List<Pair<ParentLjfdEntity, ChildBaseLjfdEntity>> pairList = new ArrayList<>();
        for (int i=0; i<recSize; i++) {
            int s = i + 1;
            String sid = "sid-%s".formatted(s);
            ParentLjfdEntity parentLjfdEntityDraft = new ParentLjfdEntity();
            parentLjfdEntityDraft.setSid(sid);
            parentLjfdEntityDraft.setName(sid);
            ParentLjfdEntity parentLjfdEntitySaved = this.parentLjfdRepository.save(parentLjfdEntityDraft);
            log.info("prepare_parentLjfdEntity_basic - parentLjfdEntitySaved: [{}]", parentLjfdEntitySaved);
            ChildBaseLjfdEntity childBaseLjfdEntitySaved;
            if (s % 2 == 0) {
                ChildTwoLjfdEntity childTwoLjfdEntity2 = new ChildTwoLjfdEntity();
                childTwoLjfdEntity2.setName(sid);
                childTwoLjfdEntity2.setParentLjfdEntity(parentLjfdEntitySaved);
                childBaseLjfdEntitySaved = this.childTwoLjfdRepository.save(childTwoLjfdEntity2);
            }
            else if (s % 3 == 0) {
                ChildThreeLjfdEntity childThreeLjfdEntity3 = new ChildThreeLjfdEntity();
                childThreeLjfdEntity3.setName(sid);
                childThreeLjfdEntity3.setParentLjfdEntity(parentLjfdEntitySaved);
                childBaseLjfdEntitySaved = this.childThreeLjfdRepository.save(childThreeLjfdEntity3);
            }
            else {
                ChildOneLjfdEntity childOneLjfdEntity1 = new ChildOneLjfdEntity();
                childOneLjfdEntity1.setName(sid);
                childOneLjfdEntity1.setParentLjfdEntity(parentLjfdEntitySaved);
                childBaseLjfdEntitySaved = this.childOneLjfdRepository.save(childOneLjfdEntity1);
            }
            pairList.add(Pair.with(parentLjfdEntitySaved, childBaseLjfdEntitySaved));
        }
//        this.parentLjfdRepository.flush();
//        this.childOneLjfdRepository.flush();
//        this.childTwoLjfdRepository.flush();
//        this.childThreeLjfdRepository.flush();
        this.entityManager.flush();
        this.entityManager.clear();
        //
        int delSize = random.nextInt(1, Math.round(((float) pairList.size() / 3)));
        Set<Pair<ParentLjfdEntity, ChildBaseLjfdEntity>> delPairSet = new HashSet<>();
        for (int i=0; i<delSize; i++) {
            int delIndex = random.nextInt(0, pairList.size());
            Pair<ParentLjfdEntity, ChildBaseLjfdEntity> delPair = pairList.get(delIndex);
            delPairSet.add(delPair);
        }
        delPairSet.forEach(delPair -> {
            this.childOneLjfdRepository.delete(new Specification<ChildOneLjfdEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildOneLjfdEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjfdEntity_basic - childOneLjfdRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
////////                    return criteriaBuilder.equal(root.get("parentLjfdEntity").get("sid"), delEntity.getSid());
//////                    Join<ChildOneLjfdEntity, ParentLjfdEntity> join = root.join("parentLjfdEntity");
//////                    return criteriaBuilder.equal(join.get("sid"), delEntity.getSid());
////                    return criteriaBuilder.equal(root.get("parentSid"), delEntity.getSid());
//                    return criteriaBuilder.equal(root.get("parentLjfdEntity"), delPair);
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.childTwoLjfdRepository.delete(new Specification<ChildTwoLjfdEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildTwoLjfdEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjfdEntity_basic - childTwoLjfdRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
////////                    return criteriaBuilder.equal(root.get("parentLjfdEntity").get("sid"), delEntity.getSid());
//////                    Join<ChildTwoLjfdEntity, ParentLjfdEntity> join = root.join("parentLjfdEntity");
//////                    return criteriaBuilder.equal(join.get("sid"), delEntity.getSid());
////                    return criteriaBuilder.equal(root.get("parentSid"), delEntity.getSid());
//                    return criteriaBuilder.equal(root.get("parentLjfdEntity"), delPair);
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.childThreeLjfdRepository.delete(new Specification<ChildThreeLjfdEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildThreeLjfdEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjfdEntity_basic - childThreeLjfdRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
////////                    return criteriaBuilder.equal(root.get("parentLjfdEntity").get("sid"), delEntity.getSid());
//////                    Join<ChildThreeLjfdEntity, ParentLjfdEntity> join = root.join("parentLjfdEntity");
//////                    return criteriaBuilder.equal(join.get("sid"), delEntity.getSid());
////                    return criteriaBuilder.equal(root.get("parentSid"), delEntity.getSid());
//                    return criteriaBuilder.equal(root.get("parentLjfdEntity"), delPair);
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.parentLjfdRepository.deleteById(delPair.getValue0().getId());
        });
        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void test_leftJoinFetchDel_basic() {
        log.info("test_leftJoinFetchDel_basic - start");

//        JpaObjectRetrievalFailureException exception = assertThrows(JpaObjectRetrievalFailureException.class, () -> {
        Exception exception = assertThrows(Exception.class, () -> {

//        // select cole1_0.id,cole1_0.name,cole1_0.parent_sid,cole1_0.version from tbl_ljf_child_one cole1_0
//        List<ChildOneLjfdEntity> childOneLjfdEntityList = this.childOneLjfdRepository.findAll();
//        log.info("test_leftJoinFetchDel_basic - childOneLjfdEntityList.size: [{}]", childOneLjfdEntityList.size());
//        childOneLjfdEntityList.forEach(e -> {
//            log.info("test_leftJoinFetchDel_basic - childOneLjfdEntityList - e: [{}]", e.toString());
//        });
//
//        // select ctle1_0.id,ctle1_0.name,ctle1_0.parent_sid,ctle1_0.version from tbl_ljf_child_two ctle1_0
//        List<ChildTwoLjfdEntity> childTwoLjfdEntityList = this.childTwoLjfdRepository.findAll();
//        log.info("test_leftJoinFetchDel_basic - childTwoLjfdEntityList.size: [{}]", childTwoLjfdEntityList.size());
//
//        // select ctle1_0.id,ctle1_0.name,ctle1_0.parent_sid,ctle1_0.version from tbl_ljf_child_three ctle1_0
//        List<ChildThreeLjfdEntity> childThreeLjfdEntityList = this.childThreeLjfdRepository.findAll();
//        log.info("test_leftJoinFetchDel_basic - childThreeLjfdEntityList.size: [{}]", childThreeLjfdEntityList.size());

        // select ple1_0.id,ple1_0.sid,ple1_0.name,ple1_0.version from tbl_ljf_parent ple1_0
        List<ParentLjfdEntity> parentLjfdEntityList = this.parentLjfdRepository.findAll();
        log.info("test_leftJoinFetchDel_basic - parentLjfdEntityList.size: [{}]", parentLjfdEntityList.size());
        parentLjfdEntityList.forEach(e -> {
            log.info("test_leftJoinFetchDel_basic - parentLjfdEntityList - e: [{}]", e.toString());
        });

        });
        assertThat(exception.getMessage()).contains("with identifier value");

        log.info("test_leftJoinFetchDel_basic - end");
    }

}
