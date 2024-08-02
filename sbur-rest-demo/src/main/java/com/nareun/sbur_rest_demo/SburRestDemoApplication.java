package com.nareun.sbur_rest_demo;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.nareun.sbur_rest_demo.config.Droid;
import com.nareun.sbur_rest_demo.entity.Coffee;
import com.nareun.sbur_rest_demo.repository.CoffeeRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication // * @SpringBootApplication > @SpringBootConfiguration > @Configuration
@ConfigurationPropertiesScan // * ConfigurationProperties를 스캔
public class SburRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

	//* 이런 식으로 서드파티 컴포넌트를 감싸고 이 속성을 애플리케이션 환경에 통합.
	@Bean
	@ConfigurationProperties(prefix = "droid")
	Droid createDroid() {
		return new Droid();
	}

}

@Component
class DataLoader {
	private final CoffeeRepository coffeeRepository;

	public DataLoader(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}

	@PostConstruct
	private void loadData() {
		coffeeRepository.saveAll(List.of(
				new Coffee("Cafe Cereza"),
				new Coffee("Cafe Ganador"),
				new Coffee("Cafe Lareno"),
				new Coffee("Cafe Tres Pontas")));
	}
}