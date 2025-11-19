package com.siukatech.poc.spring.jpa.demo.leftjoiniddel.service;

import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildOneLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildThreeLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ChildTwoLjidRepository;
import com.siukatech.poc.spring.jpa.demo.leftjoiniddel.repository.ParentLjidRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LeftJoinIdDelService {

    private final ParentLjidRepository parentLjidRepository;
    private final ChildOneLjidRepository childOneLjidRepository;
    private final ChildTwoLjidRepository childTwoLjidRepository;
    private final ChildThreeLjidRepository childThreeLjidRepository;

    public LeftJoinIdDelService(ParentLjidRepository parentLjidRepository
            , ChildOneLjidRepository childOneLjidRepository
            , ChildTwoLjidRepository childTwoLjidRepository
            , ChildThreeLjidRepository childThreeLjidRepository) {
        this.parentLjidRepository = parentLjidRepository;
        this.childOneLjidRepository = childOneLjidRepository;
        this.childTwoLjidRepository = childTwoLjidRepository;
        this.childThreeLjidRepository = childThreeLjidRepository;
    }
}
