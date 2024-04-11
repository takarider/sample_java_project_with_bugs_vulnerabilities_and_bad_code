package com.example.demo.model;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("birth_date"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("credit_card"),
                rs.getString("debit_card"),
                rs.getString("contact_number_1"),
                rs.getString("contact_number_2")
        );
    }
}
