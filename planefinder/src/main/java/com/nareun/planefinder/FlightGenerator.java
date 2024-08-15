package com.nareun.planefinder;

import org.springframework.stereotype.Component;
import java.util.Random;
import java.util.List;

@Component
public class FlightGenerator {
    private final Random rnd = new Random();

    // *다양한 항공기 모델의 목록
    List<String> typeList = List.of("A319", "A320", "A321", // Airbus
            "BE33", "BE36", // Beechcraft
            "B737", "B739", "B763", // Boeing
            "C172", "C402", "C560", // Cessna
            "E50P", "E75L", // Embraer
            "MD11", // McDonnell Douglas!
            "PA28", "PA32", "PA46"); // Piper

    Aircraft generate() {
        // *SAL + 무작위 숫자 (0-999) 
        String csfn = "SAL" + rnd.nextInt(1000);

        return new Aircraft(csfn,
                // *레지스트레이션 번호: "N" + 5자리 무작위 숫자 (앞에 0으로 패딩) 
                "N" + String.format("%1$5s", rnd.nextInt(10000)).replace(' ', '0'),
                csfn,
                typeList.get(rnd.nextInt(typeList.size())),
                // *고도: 0-39999 사이의 무작위 정수
                rnd.nextInt(40000),
                // *방향: 0-359 사이의 무작위 정수
                rnd.nextInt(360),
                // *속도: 100-499 사이의 무작위 정수
                rnd.ints(1, 100, 500).iterator().next().intValue(), // airspeed
                // *위도: 35-42 사이의 무작위 실수
                rnd.doubles(1, 35d, 42d).iterator().next().floatValue(), // latitude
                // *경도: -115에서 -83 사이의 무작위 실수
                rnd.doubles(1, -115d, -83d).iterator().next().floatValue()); // longitude
    }
}
