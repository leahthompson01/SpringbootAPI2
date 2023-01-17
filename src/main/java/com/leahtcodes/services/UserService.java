package com.leahtcodes.services;

import com.leahtcodes.Models.Provider;
import com.leahtcodes.Models.Userss;
import com.leahtcodes.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public void processOAuthPostLogin(String username) {
        Userss existUser = repo.getUserByUsername(username);
        if(existUser == null){
            Userss newUser  = new Userss();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            repo.save(newUser);
        }

    }

}
