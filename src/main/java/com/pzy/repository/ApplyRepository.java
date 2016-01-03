package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Apply;
public interface ApplyRepository extends PagingAndSortingRepository<Apply, Long>,JpaSpecificationExecutor<Apply>{
}

