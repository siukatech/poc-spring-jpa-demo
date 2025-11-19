package com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.service;

import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildOneLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildThreeLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ChildTwoLjfdRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoinfetchdel.repository.ParentLjfdRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeftJoinFetchDelService {

    private final ParentLjfdRepository parentLjfdRepository;
    private final ChildOneLjfdRepository childOneLjfdRepository;
    private final ChildTwoLjfdRepository childTwoLjfdRepository;
    private final ChildThreeLjfdRepository childThreeLjfdRepository;

    public LeftJoinFetchDelService(ParentLjfdRepository parentLjfdRepository
            , ChildOneLjfdRepository childOneLjfdRepository
            , ChildTwoLjfdRepository childTwoLjfdRepository
            , ChildThreeLjfdRepository childThreeLjfdRepository) {
        this.parentLjfdRepository = parentLjfdRepository;
        this.childOneLjfdRepository = childOneLjfdRepository;
        this.childTwoLjfdRepository = childTwoLjfdRepository;
        this.childThreeLjfdRepository = childThreeLjfdRepository;
    }
}
