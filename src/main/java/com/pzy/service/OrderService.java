
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

import com.pzy.entity.Order;
import com.pzy.repository.OrderRepository;

@Service
public class OrderService {
     @Autowired
     private OrderRepository orderRepository;
     public List<Order> findAll() {
          return (List<Order>) orderRepository.findAll();
     }
     public Page<Order> findAll(final int pageNumber, final int pageSize,final String id){
               PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
              
               Specification<Order> spec = new Specification<Order>() {
                    @Override
                    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (id != null) {
                    	 predicate.getExpressions().add(cb.equal(root.get("id").as(String.class), id));
                    }
                    return predicate;
                    }
               };
               Page<Order> result = (Page<Order>) orderRepository.findAll(spec, pageRequest);
               return result;
     }
     public Page<Order> findAll(final int pageNumber, final int pageSize,final String id,final String state){
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
        
         Specification<Order> spec = new Specification<Order>() {
              @Override
              public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (id != null) {
                   predicate.getExpressions().add(cb.equal(root.get("id").as(String.class), id));
              }
              return predicate;
              }
         };
         Page<Order> result = (Page<Order>) orderRepository.findAll(spec, pageRequest);
         return result;
     }
     public void delete(String id){
          orderRepository.delete(id);
     }
     public Order find(String id){
    	  return orderRepository.findOne(id);
     }
     public void save(Order order){
    	orderRepository.save(order);
     }
     public void save(List<Order> order){
     	orderRepository.save(order);
      }
}