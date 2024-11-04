package com.nareun.neo.repository;

import org.springframework.data.repository.CrudRepository;

import com.nareun.neo.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {

}
