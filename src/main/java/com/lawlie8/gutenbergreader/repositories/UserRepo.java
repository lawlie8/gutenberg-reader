package com.lawlie8.gutenbergreader.repositories;

import com.lawlie8.gutenbergreader.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

    @Query(value = "select * from users where user_name = :userName",nativeQuery = true)
    Users findByUserNameNative(String userName);

}
