
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

import com.pzy.entity.Club;
import com.pzy.repository.ClubRepository;

@Service
public class ClubService {
     @Autowired
     private ClubRepository clubRepository;
     public List<Club> findAll() {
          return (List<Club>) clubRepository.findAll();
     }
     public Page<Club> findAll(final int pageNumber, final int pageSize,final String clubName){
               PageRequest pageRequest = new PageRequest(pageNumber - 1, pageSize, new Sort(Direction.DESC, "id"));
              
               Specification<Club> spec = new Specification<Club>() {
                    @Override
                    public Predicate toPredicate(Root<Club> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.conjunction();
                    if (clubName != null) {
                         predicate.getExpressions().add(cb.like(root.get("name").as(String.class), clubName+"%"));
                    }
                    return predicate;
                    }
               };
               Page<Club> result = (Page<Club>) clubRepository.findAll(spec, pageRequest);
               return result;
     }
     public void delete(Long id){
          clubRepository.delete(id);
     }
     public Club find(Long id){
    	  return clubRepository.findOne(id);
     }
     public void save(Club Club){
    	 clubRepository.save(Club);
     }
}