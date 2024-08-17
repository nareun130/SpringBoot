package com.nareun.aircraft_mysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.nareun.aircraft_mysql.domain.Aircraft;

public interface AircraftRepository extends CrudRepository<Aircraft, Long> {

}
