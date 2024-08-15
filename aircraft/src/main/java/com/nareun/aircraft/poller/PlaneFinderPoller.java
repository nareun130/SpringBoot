package com.nareun.aircraft.poller;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nareun.aircraft.domain.Aircraft;
import com.nareun.aircraft.repository.AircraftRepository;

@EnableScheduling
@Component public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
    private final RedisConnectionFactory connectionFactory;
    private final AircraftRepository repository;
    PlaneFinderPoller(RedisConnectionFactory connectionFactory, AircraftRepository repository) {
        this.connectionFactory = connectionFactory;
        this.repository = repository;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes() {
          //*자동 연결된 ConnecdtionFactory로 DB연결 -> flujshDb로 존재하는 모든 키 삭제
        connectionFactory.getConnection().serverCommands().flushDb();

        //* PlaneFinder서비스 호출
        client.get()
                .retrieve() 
                //응답 body는 Flux로 변환
                .bodyToFlux(Aircraft.class)
                //등록번호가 없는 Aircraft를 제거
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                //키/값 쌍 저장
                .forEach(repository::save);
        
        repository.findAll().forEach(System.out::println);

    }
}
 