package com.example.demo_for_batch7.repository;

import com.example.demo_for_batch7.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, String> {


    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String Password);
    List<User> findByNameContaining(String keywords);
}