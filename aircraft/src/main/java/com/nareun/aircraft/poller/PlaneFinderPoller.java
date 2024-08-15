package com.nareun.aircraft.poller;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nareun.aircraft.domain.Aircraft;

@EnableScheduling
@Component
public class PlaneFinderPoller {
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
    private final RedisConnectionFactory connectionFactory;
    private final RedisOperations<String, Aircraft> redisOperations;

    PlaneFinderPoller(RedisConnectionFactory connectionFactory, RedisOperations<String, Aircraft> redisOperations) {
        this.connectionFactory = connectionFactory;
        this.redisOperations = redisOperations;
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
                .forEach(ac -> redisOperations.opsForValue().set(ac.getReg(), ac));
        
        redisOperations.opsForValue()
                .getOperations()
                //모든 키 조회
                .keys("*")
                //항공기 값을 조회하고 출력
                .forEach(ac -> System.out.println(redisOperations.opsForValue().get(ac)));

    }
}
 