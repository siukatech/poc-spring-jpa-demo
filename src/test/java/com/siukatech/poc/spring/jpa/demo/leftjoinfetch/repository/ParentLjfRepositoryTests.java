package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ChildOneLjfEntity;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ChildThreeLjfEntity;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ChildTwoLjfEntity;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.entity.ParentLjfEntity;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

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
public class ParentLjfRepositoryTests {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ParentLjfRepository parentLjfRepository;

    @Autowired
    private ChildOneLjfRepository childOneLjfRepository;

    @Autowired
    private ChildTwoLjfRepository childTwoLjfRepository;

    @Autowired
    private ChildThreeLjfRepository childThreeLjfRepository;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
        //
        this.prepare_parentLjfEntity_basic();
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    private void prepare_parentLjfEntity_basic() {
        int size = 0;
        size = 10;
        for (int i=0; i<size; i++) {
            int s = i + 1;
            String sid = "sid-%s".formatted(s);
            ParentLjfEntity parentLjfEntityDraft = new ParentLjfEntity();
            parentLjfEntityDraft.setSid(sid);
            parentLjfEntityDraft.setName(sid);
            ParentLjfEntity parentLjfEntitySaved = this.parentLjfRepository.save(parentLjfEntityDraft);
            log.info("prepare_parentLjfEntity_basic - parentLjfEntitySaved: [{}]", parentLjfEntitySaved);
            if (s % 2 == 0) {
                ChildTwoLjfEntity childTwoLjfEntity2 = new ChildTwoLjfEntity();
                childTwoLjfEntity2.setName(sid);
                childTwoLjfEntity2.setParentLjfEntity(parentLjfEntitySaved);
                this.childTwoLjfRepository.save(childTwoLjfEntity2);
                parentLjfEntitySaved.setChildTwoLjfEntity(childTwoLjfEntity2);
                parentLjfEntitySaved = this.parentLjfRepository.save(parentLjfEntitySaved);
            }
            else if (s % 3 == 0) {
                ChildThreeLjfEntity childThreeLjfEntity3 = new ChildThreeLjfEntity();
                childThreeLjfEntity3.setName(sid);
                childThreeLjfEntity3.setParentLjfEntity(parentLjfEntitySaved);
                this.childThreeLjfRepository.save(childThreeLjfEntity3);
                parentLjfEntitySaved.setChildThreeLjfEntity(childThreeLjfEntity3);
                parentLjfEntitySaved = this.parentLjfRepository.save(parentLjfEntitySaved);
            }
            else {
                ChildOneLjfEntity childOneLjfEntity1 = new ChildOneLjfEntity();
                childOneLjfEntity1.setName(sid);
                childOneLjfEntity1.setParentLjfEntity(parentLjfEntitySaved);
                this.childOneLjfRepository.save(childOneLjfEntity1);
                parentLjfEntitySaved.setChildOneLjfEntity(childOneLjfEntity1);
                parentLjfEntitySaved = this.parentLjfRepository.save(parentLjfEntitySaved);
            }
        }
//        this.parentLjfRepository.flush();
//        this.childOneLjfRepository.flush();
//        this.childTwoLjfRepository.flush();
//        this.childThreeLjfRepository.flush();
        this.entityManager.flush();
        this.entityManager.clear();
    }

    @Test
    public void test_leftJoinFetch_basic() {
        log.info("test_leftJoinFetch_basic - start");

//        // select cole1_0.id,cole1_0.name,cole1_0.parent_id,cole1_0.version from tbl_ljf_child_one cole1_0
//        List<ChildOneLjfEntity> childOneLjfEntityList = this.childOneLjfRepository.findAll();
//        log.info("test_leftJoinFetch_basic - childOneLjfEntityList.size: [{}]", childOneLjfEntityList.size());
//        childOneLjfEntityList.forEach(e -> {
//            log.info("test_leftJoinFetch_basic - childOneLjfEntityList - e: [{}]", e.toString());
//        });
//
//        // select ctle1_0.id,ctle1_0.name,ctle1_0.parent_id,ctle1_0.version from tbl_ljf_child_two ctle1_0
//        List<ChildTwoLjfEntity> childTwoLjfEntityList = this.childTwoLjfRepository.findAll();
//        log.info("test_leftJoinFetch_basic - childTwoLjfEntityList.size: [{}]", childTwoLjfEntityList.size());
//
//        // select ctle1_0.id,ctle1_0.name,ctle1_0.parent_id,ctle1_0.version from tbl_ljf_child_three ctle1_0
//        List<ChildThreeLjfEntity> childThreeLjfEntityList = this.childThreeLjfRepository.findAll();
//        log.info("test_leftJoinFetch_basic - childThreeLjfEntityList.size: [{}]", childThreeLjfEntityList.size());

        // select ple1_0.id,ple1_0.sid,ple1_0.name,ple1_0.version from tbl_ljf_parent ple1_0
        List<ParentLjfEntity> parentLjfEntityList = this.parentLjfRepository.findAll();
        log.info("test_leftJoinFetch_basic - parentLjfEntityList.size: [{}]", parentLjfEntityList.size());
        parentLjfEntityList.forEach(e -> {
            log.info("test_leftJoinFetch_basic - parentLjfEntityList - e: [{}]", e.toString());
        });

        log.info("test_leftJoinFetch_basic - end");
    }

}
