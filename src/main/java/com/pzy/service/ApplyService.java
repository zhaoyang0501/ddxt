
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
import com.pzy.entity.Apply;
import com.pzy.repository.ApplyRepository;

@Service
public class ApplyService {
     @Autowired
     private ApplyRepository applyRepository;
     public List<Apply> findAll() {
          return (List<Apply>) applyRepository.findAll();
     }
     public Page<Apply> findAll(final int pageNumber, final int pageSize,final String applyName){
               PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
              
               Specification<Apply> spec = new Specification<Apply>() {
                    @Override
                    public Predicate toPredicate(Root<Apply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (applyName != null) {
                         predicate.getExpressions().add(cb.like(root.get("name").as(String.class), applyName+"%"));
                    }
                    return predicate;
                    }
               };
               Page<Apply> result = (Page<Apply>) applyRepository.findAll(spec, pageRequest);
               return result;
     }
     public void delete(Long id){
          applyRepository.delete(id);
     }
     public Apply find(Long id){
    	  return applyRepository.findOne(id);
     }
     public void save(Apply apply){
    	 applyRepository.save(apply);
     }
}