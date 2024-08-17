package com.nareun.aircraft_kotlin

import org.springframework.data.repository.CrudRepository

interface AircraftRepository: CrudRepository<Aircraft,String>