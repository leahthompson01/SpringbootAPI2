package com.leahtcodes.Controllers;

import com.leahtcodes.Models.Coffee;
import com.leahtcodes.Models.Userss;
import com.leahtcodes.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.web.bind.annotation.*;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Userss user, OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());
                        user.setUsername(authentication.getName()) ;
        //...
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        user.setEmail((String) attributes.get("email"));
        user.setGoogleId((String) attributes.get("sub"));



        userRepository.save(user);
        return user.getUsername();
    }

    @GetMapping("")
    public List<Userss> getAllUsers(){
        return (List<Userss>) userRepository.findAll();
    }

    @GetMapping("/id")
    public Optional<Userss> findUserById(@RequestParam(value = "id",required = true) Long id){
        return userRepository.findById(id);

    }
    @GetMapping("/coffees")
    public List<Coffee> getAllCoffees( OAuth2AuthenticationToken authentication ){
        OAuth2AuthorizedClient client = authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
       String googleId = (String) attributes.get("sub");
        Userss user = userRepository.findByGoogleId(googleId);
            return user.getCoffees();
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
