package com.pzy.repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.Dept;
public interface DeptRepository extends PagingAndSortingRepository<Dept, Long>,JpaSpecificationExecutor<Dept>{
}

