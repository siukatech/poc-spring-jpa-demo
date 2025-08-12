package com.siukatech.poc.spring.jpa.demo.simple.repository;

import com.siukatech.poc.spring.jpa.demo.simple.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.TestPropertySource;

import java.util.Objects;

@Slf4j
//@SpringBootTest(classes = {UserRepository.class})
@DataJpaTest
@TestPropertySource(properties = {
        "logging.level.org.hibernate.SQL=DEBUG"
        , "logging.level.org.hibernate.orm.jdbc.bind=TRACE"
        , "logging.level.com.siukatech.poc.spring.jpa.demo=DEBUG"
})
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

//    @PersistenceContext
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setup(TestInfo testInfo) {
        log.info("setup - testInfo: [{}]", testInfo);
    }

    @AfterEach
    public void teardown(TestInfo testInfo) {
        log.info("teardown - testInfo: [{}]", testInfo);
    }

    /**
     * Only TxType.NEVER and TxType.SUPPORTS are ok.
     * Others are same as no @Transactional.
     *
     * Can use TestEntityManager to detach the Entity for triggering OptimisticLocking
     */
////    @Transactional(value = Transactional.TxType.NEVER)
//    @Transactional(value = Transactional.TxType.SUPPORTS)
    @Test
    public void test_optimisticException_basic() {
        // given
        Long seqId = 1L;
        String userId = "user-1";
        String userName = "Peter";
        UserEntity userEntitySrc = new UserEntity();
//        userEntitySrc.setId(seqId);
        userEntitySrc.setUserId(userId);
        userEntitySrc.setName(userName);
        userEntitySrc.setVersion(1L);
        this.userRepository.save(userEntitySrc);
        //
        UserEntity userEntityRe1 = userRepository.findById(seqId)
                .orElseThrow(() -> new EntityNotFoundException("User not found [%s]".formatted(seqId)));
        userEntityRe1.setName("Peter-Re1");
//        this.entityManager.detach(userEntityRe1);
        this.userRepository.save(userEntityRe1);
        //
        UserEntity userEntityRe2 = userRepository.findById(seqId)
                .orElseThrow(() -> new EntityNotFoundException("User not found [%s]".formatted(seqId)));
        //
        log.info("test_optimisticException_basic_v1 - userEntityRe1.getId: [" + userEntityRe1.getId()
                + "], userEntityRe1.getName: [" + userEntityRe1.getName()
                + "], userEntityRe1.getVersion: [" + userEntityRe1.getVersion()
                + "], userEntityRe2.getId: [" + userEntityRe2.getId()
                + "], userEntityRe2.getName: [" + userEntityRe2.getName()
                + "], userEntityRe2.getVersion: [" + userEntityRe2.getVersion()
                + "]");
        //
         //
//        UserEntity userEntityRe3 = new UserEntity();
//        userEntityRe3.setId(seqId);
        UserEntity userEntityRe3 = this.userRepository.findById(seqId)
                .orElseThrow(() -> new EntityNotFoundException("User not found [%s]".formatted(seqId)));
        userEntityRe3.setUserId(userId);
        userEntityRe3.setName("Peter-Re1-Re2");
        userEntityRe3.setVersion(1L);
        //
        UserEntity userEntityAir1 = new UserEntity();
        Exception objectOptimisticLockingFailureException = Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            this.entityManager.detach(userEntityRe3);
            UserEntity userEntityTemp1 = this.userRepository.save(userEntityRe3);
            log.info("test_optimisticException_basic_v1 - userEntityTemp1.getId: [" + userEntityTemp1.getId()
                    + "], userEntityTemp1.getName: [" + userEntityTemp1.getName()
                    + "], userEntityTemp1.getVersion: [" + userEntityTemp1.getVersion()
                    + "]");
            userEntityAir1.setId(userEntityTemp1.getId());
            userEntityAir1.setUserId(userEntityTemp1.getUserId());
            userEntityAir1.setVersion(userEntityTemp1.getVersion());
            userEntityAir1.setName(userEntityTemp1.getName());
        });
        //
        if (Objects.isNull(userEntityAir1.getId())) {
            log.info("test_optimisticException_basic_v1 - objectOptimisticLockingFailureException has thrown that is why userEntityAir1.getId is NULL");
        }
        log.info("test_optimisticException_basic_v1 - userEntityRe3.getId: [" + userEntityRe3.getId()
                + "], userEntityRe3.getName: [" + userEntityRe3.getName()
                + "], userEntityRe3.getVersion: [" + userEntityRe3.getVersion()
                + "], userEntityAir1.getId: [" + userEntityAir1.getId()
                + "], userEntityAir1.getName: [" + userEntityAir1.getName()
                + "], userEntityAir1.getVersion: [" + userEntityAir1.getVersion()
                + "], objectOptimisticLockingFailureException.getClass.getName: [" + (objectOptimisticLockingFailureException.getClass().getName())
                + "], objectOptimisticLockingFailureException.getMessage: [" + (objectOptimisticLockingFailureException.getMessage())
                + "]");
        log.error("test_optimisticException_basic_v1 - objectOptimisticLockingFailureException: ", objectOptimisticLockingFailureException);

    }

}
