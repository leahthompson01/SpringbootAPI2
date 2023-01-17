package com.leahtcodes.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity @Data @NoArgsConstructor
public class Coffee implements Serializable {

    @Id
    @SequenceGenerator(
            name="coffee_id+sequence",
            sequenceName = "coffee_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "coffee_id_sequence"
    )
    private Integer Id;

    private Integer user_id;
    @NotEmpty(message="Name may not be empty")
    private String coffeeName;
    @NotEmpty(message="Brand may not be empty")
    private String brand;
    @NotNull(message="Price may not be null") private String roast;
    private Integer coffeePrice;

    public Coffee(String name, String brand, String roast, Integer price, Integer user_id){
        this.coffeeName = name;
        this.brand = brand;
        this.roast = roast;
        this.coffeePrice = price;
        this.user_id = user_id;
    }



    public enum Provider {
        LOCAL, GOOGLE
    }
}
