
package com.pzy.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.pzy.entity.Order;
import com.pzy.entity.PayOrder;
import com.pzy.repository.OrderRepository;
import com.pzy.repository.PayOrderRepository;

@Service
public class PayOrderService {
     @Autowired
     private PayOrderRepository payOrderRepository;
     @Autowired
     private OrderRepository orderRepository;
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
                        predicate.getExpressions().add(cb.equal(root.get("oid").as(String.class), id));
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
     
     
     public Page<PayOrder> findAllByUser(final int pageNumber, final int pageSize,
    		 final Long user,final String begin,final String end,final String state) throws Exception{
         PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
        
         Specification<PayOrder> spec = new Specification<PayOrder>() {
              @Override
              public Predicate toPredicate(Root<PayOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (user != null) {
                   predicate.getExpressions().add(cb.equal(root.get("user").get("id").as(String.class), user));
              }
              if (state != null) {
                  predicate.getExpressions().add(cb.equal(root.get("state").as(String.class), state));
              }
              if (StringUtils.isNotBlank(begin)) {
                  try {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("submitDate").as(Date.class), DateUtils.parseDate(begin, "yyyy-MM-dd")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
              if (StringUtils.isNotBlank(end)) {
                  try {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("submitDate").as(Date.class), DateUtils.parseDate(end, "yyyy-MM-dd")));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
              }
              return predicate;
              }
         };
         Page<PayOrder> result = (Page<PayOrder>) payOrderRepository.findAll(spec, pageRequest);
         return result;
     }
     public Integer findAllByUserAndState(final Long user,final String state) {
         Specification<PayOrder> spec = new Specification<PayOrder>() {
              @Override
              public Predicate toPredicate(Root<PayOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if (user != null) {
                   predicate.getExpressions().add(cb.equal(root.get("user").get("id").as(String.class), user));
              }
              if (state != null) {
                  predicate.getExpressions().add(cb.equal(root.get("state").as(String.class), state));
              }
              return predicate;
              }
         };
         return  payOrderRepository.findAll(spec).size();
     }
     public void delete(Long id){
          payOrderRepository.delete(id);
     }
     public PayOrder find(Long id){
    	  return payOrderRepository.findOne(id);
     }
     public List<PayOrder> findByOid(String oid){
   	  return payOrderRepository.findByOid(oid);
    }
     public void save(PayOrder payOrder){
    	payOrderRepository.save(payOrder);
     }
     public void save(List<PayOrder> payOrder){
     	payOrderRepository.save(payOrder);
      }
     
     public void updateAll(){
    	 List<PayOrder> payOrders=payOrderRepository.findByState("商家未确认");
    	 for(PayOrder payOrder:payOrders){
    		 Order order=orderRepository.findOne(payOrder.getOid());
    		 if(order!=null){
    			 payOrder.setOrder(order);
    			 payOrder.setState("已确认订单");
    		 }
    		 payOrderRepository.save(payOrder);
    	 }
     }
}