package com.pzy.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.pzy.entity.PayOrder;
public interface PayOrderRepository extends PagingAndSortingRepository<PayOrder, Long>,JpaSpecificationExecutor<PayOrder>{
	public List<PayOrder> findByOid(String oid); 
	public List<PayOrder> findByState(String state); 
}

