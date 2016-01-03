package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Club;
public interface ClubRepository extends PagingAndSortingRepository<Club, Long>,JpaSpecificationExecutor<Club>{
}

