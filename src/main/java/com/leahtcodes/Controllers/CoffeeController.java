package com.leahtcodes.Controllers;

import com.leahtcodes.Models.Coffee;
import com.leahtcodes.Repositories.CoffeeRepository;
import com.leahtcodes.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/coffees")
public class CoffeeController {

    private final CoffeeRepository coffeeRepository;

    private OAuth2AuthorizedClientService authorizedClientService;
    public CoffeeController(CoffeeRepository coffeeRepository, OAuth2AuthorizedClientService authorizedClientService){
        this.coffeeRepository = coffeeRepository;
        this.authorizedClientService = authorizedClientService;
    }
    @GetMapping("/add")
    public List<Coffee> getCoffees(){
        return (List<Coffee>) coffeeRepository.findAll();
    }

    @GetMapping("/brand")
    public List<Coffee> getCoffeeByBrand(@RequestParam String brand){
        return coffeeRepository.findCoffeeByBrand(brand.toLowerCase());
    }


    @GetMapping("/price")
    public List<Coffee> getCoffeeLessOrEqual(@RequestParam("priceLimit") Integer price){
        return coffeeRepository.findAllByPrice(price);
    }
    record NewCoffeeRequest(
            @NotEmpty(message="Name may not be empty")
            String coffeeName,
            @NotEmpty(message="Brand may not be empty")
            String brand,
            @NotNull(message="Price may not be null")
            Integer coffeePrice,
            String roast
    ){}

    @PostMapping("/add")
    public String addCoffee(@RequestBody NewCoffeeRequest request, OAuth2AuthenticationToken authentication){
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
        System.out.println(authentication.getPrincipal().getAttributes());
        //...
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        String id = (String) attributes.get("sub");
        Coffee newCoffee = new Coffee();
        newCoffee.setCoffeeName(request.coffeeName().toLowerCase());
        newCoffee.setBrand(request.brand().toLowerCase());
        newCoffee.setCoffeePrice(request.coffeePrice());
        newCoffee.setRoast(request.roast().toLowerCase());
        newCoffee.setUser_id(id);
        System.out.println(newCoffee);
        coffeeRepository.save(newCoffee);
        System.out.println("New item added to db");
        return request.coffeeName();
    }
    @DeleteMapping
    public void deleteCoffeeById(@RequestParam("coffeeId") Integer id){
        coffeeRepository.deleteById(id);
    }
    @DeleteMapping("/name")
    public void deleteCoffeeByName(@RequestParam("name") String name){
        System.out.println(name);
        coffeeRepository.deleteByCoffeeName(name.toLowerCase());
    }
    @PutMapping("{coffeeId}")
    public Coffee updateCoffee(@RequestParam("coffeeId") Integer id, @RequestBody Coffee newCoffee ) throws Exception{
        return coffeeRepository.findById(id)
                .map(item -> {
                    item.setCoffeeName(newCoffee.getCoffeeName().toLowerCase());
                    item.setBrand(newCoffee.getBrand().toLowerCase());
                    item.setCoffeePrice(newCoffee.getCoffeePrice());
                    item.setRoast(newCoffee.getRoast().toLowerCase());
                    return coffeeRepository.save(item);
                }).orElseThrow(() -> new Exception("Id not found -" + id));

    }


}
