package com.leahtcodes.Controllers;

import com.leahtcodes.Models.Coffee;
import com.leahtcodes.Models.Userss;
import com.leahtcodes.Repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<Userss> getAllUsers(){
        return (List<Userss>) userRepository.findAll();
    }

    @GetMapping("/id")
    public Optional<Userss> findUserById(@RequestParam(value = "id",required = true) Long id){
        return userRepository.findById(id);

    }
    @GetMapping("/coffees/")
    public List<Coffee> getAllCoffees(@RequestParam(value = "id", required = true) Long id){
        Optional<Userss> user = userRepository.findById(id);
        if(!user.isPresent()){
            return new ArrayList<Coffee>();
        }else{
            return user.get().getCoffees();
        }
    }
    @DeleteMapping("")
    public Userss deleteUser(@RequestParam(value="id", required = true) Long id){
        Optional<Userss> user = userRepository.findById(id);
        if(user.isPresent()){
            userRepository.deleteById(id);
            return user.get();

        }else{
            System.out.println("User with that id does not exist");
            return null;
        }
    }







}
