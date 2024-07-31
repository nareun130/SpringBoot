package com.nareun.sbur_rest_demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SburRestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SburRestDemoApplication.class, args);
	}

}

class Coffee {
	// * 한번 할당하고 절대 수정 x
	private final String id;
	private String name;

	public Coffee(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public Coffee(String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

@RestController
@RequestMapping("/coffees")
class RestApiDemoController {
	// ! PUT은 상태코드 사용 필수, POST&UDELETE는 권장, GET은 지정 x
	private List<Coffee> coffees = new ArrayList<>();

	public RestApiDemoController() {
		coffees.addAll(List.of(
				new Coffee("Cafe Cereza"),
				new Coffee("Cafe Ganador"),
				new Coffee("Cafe Lareno"),
				new Coffee("Cafe Tres Pontas")));
	}

	// @RequestMapping(value = "/coffees", method = RequestMethod.GET)
	// Iterable<Coffee> getCoffees() {
	// return coffees;
	// }

	@GetMapping
	Iterable<Coffee> getCoffees() {
		return coffees;
	}

	@GetMapping("/{id}")
	Optional<Coffee> getCoffeeById(@PathVariable(name = "id  ") String id) {
		for (Coffee c : coffees) {
			if (c.getId().equals(id)) {
				return Optional.of(c);
			}
		}
		return Optional.empty();
	}

	@PostMapping
	Coffee postCoffee(@RequestBody Coffee coffee) {
		coffees.add(coffee);
		return coffee;
	}

	// * PUT을 쓸때는 리소스가 있으면 업데이트 없으면 생성해야 한다.
	@PutMapping("/{id}")
	ResponseEntity<Coffee> putCoffee(@PathVariable(name = "id") String id, @RequestBody Coffee coffee) {
		int coffeeIndex = -1;

		for (Coffee c : coffees) {
			if (c.getId().equals(id)) {
				coffeeIndex = coffees.indexOf(c);
				coffees.set(coffeeIndex, coffee);
			}
		}
		// * 새로 생성하면 201, 수정 완료되면 200
		return (coffeeIndex == -1) ? new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED)
				: new ResponseEntity<>(coffee, HttpStatus.OK);
	}

	@DeleteMapping(" /{id}")
	void deleteCoffee(@PathVariable(name = "id") String id) {
		coffees.removeIf(c -> c.getId().equals(id));
	}
}