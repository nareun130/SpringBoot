package com.nareun.mvc.repository;

import org.springframework.data.repository.CrudRepository;

import com.nareun.mvc.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {

}
