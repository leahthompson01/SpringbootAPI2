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

    @Column(unique=true)
    private String email;
    private String username;
    private String googleId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "googleId")
    private List<Coffee> coffees;

    public Userss(String name, String email, List<Coffee> coffees, String googleId){
        this.username = name;
        this.email = email;
        this.coffees = coffees;
        this.googleId = googleId;
    }





}

