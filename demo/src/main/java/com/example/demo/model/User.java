package com.example.demo.model;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String email;
    private String birthDate;
    private int age;
    @Nullable
    private String gender;
    private String creditCard;
    private String debitCard;
    private String contactNumber1;
    private String contactNumber2;
}
