package com.example.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.example.demo.model.User;
import com.example.demo.model.UserRowMapper;

import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(int id) {
        String sql = """
                SELECT username, email, birth_date, age, gender, credit_card, debit_card, contact_number_1, contact_number_2
                FROM users
                WHERE id = ?;
                """;
        return jdbcTemplate.query(sql,new UserRowMapper(),id)
                .stream()
                .findFirst();
    } 
}
