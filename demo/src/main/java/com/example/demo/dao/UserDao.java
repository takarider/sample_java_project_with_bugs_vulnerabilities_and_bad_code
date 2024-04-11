package com.example.demo.dao;

import java.util.Optional;

import com.example.demo.model.User;

public interface UserDao {
    Optional<User> findById(int id);
}
