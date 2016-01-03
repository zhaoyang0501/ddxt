package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Dept;
import com.pzy.entity.Work;
public interface WorkRepository extends PagingAndSortingRepository<Work, Long>,JpaSpecificationExecutor<Work>{
}

