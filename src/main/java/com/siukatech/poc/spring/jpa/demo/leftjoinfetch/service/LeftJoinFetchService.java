package com.siukatech.poc.spring.jpa.demo.leftjoinfetch.service;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository.ChildOneLjfRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository.ChildThreeLjfRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository.ChildTwoLjfRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetch.repository.ParentLjfRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeftJoinFetchService {

    private final ParentLjfRepository parentLjfRepository;
    private final ChildOneLjfRepository childOneLjfRepository;
    private final ChildTwoLjfRepository childTwoLjfRepository;
    private final ChildThreeLjfRepository childThreeLjfRepository;

    public LeftJoinFetchService(ParentLjfRepository parentLjfRepository
            , ChildOneLjfRepository childOneLjfRepository
            , ChildTwoLjfRepository childTwoLjfRepository
            , ChildThreeLjfRepository childThreeLjfRepository) {
        this.parentLjfRepository = parentLjfRepository;
        this.childOneLjfRepository = childOneLjfRepository;
        this.childTwoLjfRepository = childTwoLjfRepository;
        this.childThreeLjfRepository = childThreeLjfRepository;
    }
}
