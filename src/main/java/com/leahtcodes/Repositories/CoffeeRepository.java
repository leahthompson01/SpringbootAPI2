package com.leahtcodes.Repositories;

import com.leahtcodes.Models.Coffee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoffeeRepository extends CrudRepository<Coffee, Integer> {
    Integer deleteByCoffeeName(String name);
    List<Coffee> findCoffeeByBrand(String brand);

    @Query(value = "SELECT c.* FROM Coffee c WHERE c.price <=:price", nativeQuery = true)
    List<Coffee> findAllByPrice(@Param("price") Integer price);
}
