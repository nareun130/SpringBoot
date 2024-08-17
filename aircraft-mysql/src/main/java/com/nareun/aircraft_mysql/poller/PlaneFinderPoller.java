package com.nareun.aircraft_mysql.poller;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nareun.aircraft_mysql.domain.Aircraft;
import com.nareun.aircraft_mysql.repository.AircraftRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@EnableScheduling
@Component
@RequiredArgsConstructor
public class PlaneFinderPoller {

    @NonNull
    private final AircraftRepository repository;
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
        // repository.deleteAll();

        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);
        repository.findAll().forEach(System.out::println);
    }
}
