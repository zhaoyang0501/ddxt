
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

import com.pzy.entity.Dept;
import com.pzy.repository.DeptRepository;

@Service
public class DeptService {
     @Autowired
     private DeptRepository deptRepository;
     public List<Dept> findAll() {
          return (List<Dept>) deptRepository.findAll();
     }
     public Page<Dept> findAll(final int pageNumber, final int pageSize,final String deptName){
               PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
              
               Specification<Dept> spec = new Specification<Dept>() {
                    @Override
                    public Predicate toPredicate(Root<Dept> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (deptName != null) {
                         predicate.getExpressions().add(cb.like(root.get("name").as(String.class), deptName+"%"));
                    }
                    return predicate;
                    }
               };
               Page<Dept> result = (Page<Dept>) deptRepository.findAll(spec, pageRequest);
               return result;
     }
     public void delete(Long id){
          deptRepository.delete(id);
     }
     public Dept find(Long id){
    	  return deptRepository.findOne(id);
     }
     public void save(Dept dept){
    	 deptRepository.save(dept);
     }
}