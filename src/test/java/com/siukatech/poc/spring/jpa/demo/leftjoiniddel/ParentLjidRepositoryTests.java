package com.siukatech.poc.spring.jpa.demo.leftjoiniddel;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.entity.*;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildOneLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildThreeLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildTwoLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ParentLjidRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
public class ParentLjidRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParentLjidRepository parentLjidRepository;

    @Autowired
    private ChildOneLjidRepository childOneLjidRepository;

    @Autowired
    private ChildTwoLjidRepository childTwoLjidRepository;

    @Autowired
    private ChildThreeLjidRepository childThreeLjidRepository;

    private final Random random = new Random();

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_parentLjidEntity_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    private void prepare_parentLjidEntity_basic() {
        int recSize = 10;
        List<Pair<ParentLjidEntity, ChildBaseLjidEntity>> pairList = new ArrayList<>();
        for (int i=0; i<recSize; i++) {
            int s = i + 1;
            String sid = "sid-%s".formatted(s);
            ParentLjidEntity parentLjidEntityDraft = new ParentLjidEntity();
            parentLjidEntityDraft.setName(sid);
            ParentLjidEntity parentLjidEntitySaved = this.parentLjidRepository.save(parentLjidEntityDraft);
            log.info("prepare_parentLjidEntity_basic - parentLjidEntitySaved: [{}]", parentLjidEntitySaved);
            ChildBaseLjidEntity childBaseLjidEntitySaved;
            if (s % 2 == 0) {
                ChildTwoLjidEntity childTwoLjidEntity2 = new ChildTwoLjidEntity();
                childTwoLjidEntity2.setName(sid);
                childTwoLjidEntity2.setParentLjidEntity(parentLjidEntitySaved);
                childBaseLjidEntitySaved = this.childTwoLjidRepository.save(childTwoLjidEntity2);
            }
            else if (s % 3 == 0) {
                ChildThreeLjidEntity childThreeLjidEntity3 = new ChildThreeLjidEntity();
                childThreeLjidEntity3.setName(sid);
                childThreeLjidEntity3.setParentLjidEntity(parentLjidEntitySaved);
                childBaseLjidEntitySaved = this.childThreeLjidRepository.save(childThreeLjidEntity3);
            }
            else {
                ChildOneLjidEntity childOneLjidEntity1 = new ChildOneLjidEntity();
                childOneLjidEntity1.setName(sid);
                childOneLjidEntity1.setParentLjidEntity(parentLjidEntitySaved);
                childBaseLjidEntitySaved = this.childOneLjidRepository.save(childOneLjidEntity1);
            }
            pairList.add(Pair.with(parentLjidEntitySaved, childBaseLjidEntitySaved));
        }
        this.entityManager.flush();
        this.entityManager.clear();
        //
        int delSize = random.nextInt(1, Math.round(((float) pairList.size() / 3)));
        Set<Pair<ParentLjidEntity, ChildBaseLjidEntity>> delPairSet = new HashSet<>();
        for (int i=0; i<delSize; i++) {
            int delIndex = random.nextInt(0, pairList.size());
            Pair<ParentLjidEntity, ChildBaseLjidEntity> delPair = pairList.get(delIndex);
            delPairSet.add(delPair);
        }
        delPairSet.forEach(delPair -> {
            this.childOneLjidRepository.delete(new Specification<ChildOneLjidEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildOneLjidEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjidEntity_basic - childOneLjidRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.childTwoLjidRepository.delete(new Specification<ChildTwoLjidEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildTwoLjidEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjidEntity_basic - childTwoLjidRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.childThreeLjidRepository.delete(new Specification<ChildThreeLjidEntity>() {
                @Override
                public Predicate toPredicate(Root<ChildThreeLjidEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    log.info("prepare_parentLjidEntity_basic - childThreeLjidRepository - toPredicate - value1.getId: [{}]", delPair.getValue1().getId());
                    return criteriaBuilder.equal(root.get("id"), delPair.getValue1().getId());
                }
            });
            this.parentLjidRepository.deleteById(delPair.getValue0().getId());
        });
        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void test_leftJoinIdDel_basic() {
        log.info("test_leftJoinIdDel_basic - start");

//        JpaObjectRetrievalFailureException exception = assertThrows(JpaObjectRetrievalFailureException.class, () -> {
        Exception exception = assertThrows(Exception.class, () -> {

        // select ple1_0.id,ple1_0.sid,ple1_0.name,ple1_0.version from tbl_ljf_parent ple1_0
        List<ParentLjidEntity> parentLjidEntityList = this.parentLjidRepository.findAll();
        log.info("test_leftJoinIdDel_basic - parentLjidEntityList.size: [{}]", parentLjidEntityList.size());
        parentLjidEntityList.forEach(e -> {
            log.info("test_leftJoinIdDel_basic - parentLjidEntityList - e: [{}]", e.toString());
        });

        });
        assertThat(exception.getMessage()).contains("with identifier value");

        log.info("test_leftJoinIdDel_basic - end");
    }

}
