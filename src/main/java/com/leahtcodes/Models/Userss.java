package com.leahtcodes.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class Userss implements Serializable {
    @Id
    @SequenceGenerator(
            name="user_id+sequence",
            sequenceName = "user_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    private Integer Id;
    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String username;
    private boolean enabled;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    private List<Coffee> coffees;

    public Userss(String name, Provider provider, List<Coffee> coffees){
        this.username = name;
        this.provider = provider;
        this.coffees = coffees;
    }





}

