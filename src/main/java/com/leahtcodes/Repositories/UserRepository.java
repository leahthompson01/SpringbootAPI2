package com.leahtcodes.Repositories;

import com.leahtcodes.Models.Userss;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<Userss, Long> {
    @Query("SELECT u FROM Userss u WHERE u.username =:username")
    public Userss getUserByUsername(@Param("username") String username);
}
