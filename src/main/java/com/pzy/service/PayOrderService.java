
package com.pzy.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.PayOrder;
import com.pzy.repository.PayOrderRepository;

@Service
public class PayOrderService {
     @Autowired
     private PayOrderRepository payOrderRepository;
     public List<PayOrder> findAll() {
          return (List<PayOrder>) payOrderRepository.findAll();
     }
     public Page<PayOrder> findAll(final int pageNumber, final int pageSize,final String user,final String id,final String state){
               PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
              
               Specification<PayOrder> spec = new Specification<PayOrder>() {
                    @Override
                    public Predicate toPredicate(Root<PayOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (user != null) {
                         predicate.getExpressions().add(cb.equal(root.get("user").get("username").as(String.class), user));
                    }
                    if (id != null) {
                        predicate.getExpressions().add(cb.equal(root.get("order").get("id").as(String.class), id));
                    }
                    if (state != null) {
                        predicate.getExpressions().add(cb.equal(root.get("state").as(String.class), state));
                    }
                    return predicate;
                    }
               };
               Page<PayOrder> result = (Page<PayOrder>) payOrderRepository.findAll(spec, pageRequest);
               return result;
     }
     public void delete(Long id){
          payOrderRepository.delete(id);
     }
     public PayOrder find(Long id){
    	  return payOrderRepository.findOne(id);
     }
     public void save(PayOrder payOrder){
    	payOrderRepository.save(payOrder);
     }
     public void save(List<PayOrder> payOrder){
     	payOrderRepository.save(payOrder);
      }
}