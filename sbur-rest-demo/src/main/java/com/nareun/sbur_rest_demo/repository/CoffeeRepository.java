package com.nareun.sbur_rest_demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.nareun.sbur_rest_demo.entity.Coffee;

public interface CoffeeRepository extends CrudRepository<Coffee,String>{
    
}
