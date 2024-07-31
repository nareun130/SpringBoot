package com.nareun.sbur_rest_demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nareun.sbur_rest_demo.entity.Coffee;
import com.nareun.sbur_rest_demo.repository.CoffeeRepository;

@RestController
@RequestMapping("/coffees")
public class RestApiDemoController {

    private final CoffeeRepository coffeeRepository;

    // ! PUT은 상태코드 사용 필수, POST&UDELETE는 권장, GET은 지정 x

    public RestApiDemoController(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    @GetMapping
    Iterable<Coffee> getCoffees() {
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable(name = "id") String id) {
        return coffeeRepository.findById(id);
    }

    @PostMapping
    Coffee postCoffee(@RequestBody Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    // * PUT을 쓸때는 리소스가 있으면 업데이트 없으면 생성해야 한다.
    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable(name = "id") String id, @RequestBody Coffee coffee) {

        return (coffeeRepository.existsById(id))
                ? new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK)
                : new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable(name = "id") String id) {
        coffeeRepository.deleteById(id);
    }

}
