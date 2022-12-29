package com.flowable.usermanagement.repository;

import com.flowable.usermanagement.entity.City;
import com.flowable.usermanagement.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
    List<State> findAllByCountry(Integer country);
}
