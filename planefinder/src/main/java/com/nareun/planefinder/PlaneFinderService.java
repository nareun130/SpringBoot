package com.nareun.planefinder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class PlaneFinderService {

    private final PlaneRepository repo;
    private final FlightGenerator generator;
    private final URL acURL;
    //* JSON -> 객체로
    private final ObjectMapper om;

    @SneakyThrows//예외처리를 암묵적으로 수행
    public PlaneFinderService(PlaneRepository repo, FlightGenerator generator) {
        this.repo = repo;
        this.generator = generator;

        acURL = new URL("http://192.168.123.109/aircraft");
        om = new ObjectMapper();
    }

    public Iterable<Aircraft> getAircraft() throws IOException {
        List<Aircraft> positions = new ArrayList<>();

        JsonNode aircraftNodes = null;
        try {
            //* URL로 부터 JSON데이터를 읽음
            aircraftNodes = om.readTree(acURL)
                    .get("aircraft");

            aircraftNodes.iterator().forEachRemaining(node -> {
                try {
                    //* Aircraft객체로 변환하여 리스트에 추가
                    positions.add(om.treeToValue(node, Aircraft.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            //* 예외 발생 시 샘플 데이터를 생성하여 반환
        } catch (IOException e) {
            System.out.println("\n>>> IO Exception: " + e.getLocalizedMessage() +
                    ", generating and providing sample data.\n");
            return saveSamplePositions();
        }

        if (positions.size() > 0) {
            positions.forEach(System.out::println);

            repo.deleteAll();
            return repo.saveAll(positions);
        } else {
            System.out.println("\n>>> No positions to report, generating and providing sample data.\n");
            return saveSamplePositions();
        }
    }

    private Iterable<Aircraft> saveSamplePositions() {
        final Random rnd = new Random();

        repo.deleteAll();

        for (int i = 0; i < rnd.nextInt(10); i++) {
            repo.save(generator.generate());
        }

        return repo.findAll();
    }
}
