package com.nareun.aircraft.repository;

import org.springframework.data.repository.CrudRepository;

import com.nareun.aircraft.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft,Long>{
    
}
