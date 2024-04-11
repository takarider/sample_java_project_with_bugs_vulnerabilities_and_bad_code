package com.example.demo.controller;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.example.demo.dao.UserDao;
import com.example.demo.model.User;

import lombok.NonNull;


@RestController
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    private static final String KEY = "ThisIsASecretKey"; // Hardcoded key

    @PostMapping("/user")
    public ResponseEntity<String> createUser(@RequestBody List<User> users) {   
        int counter;
        
        @NonNull
        String gender ;

        for (User user : users) {
            try {
                int age = user.getAge();
                if (age < 0) {
                    return ResponseEntity.badRequest().body("Invalid age");
                }
                else if (age > 110) {
                    return ResponseEntity.badRequest().body("Invalid age");
                }
                else if (age < 0 ) {
                    return ResponseEntity.badRequest().body("Invalid age");
                }
                else if (age < 18) {
                    return ResponseEntity.badRequest().body("User must be 18 years old or older");
                }

                String query = "INSERT INTO users (username, email, birth_date, age, gender, credit_card, debit_card, contact_number_1, contact_number_2) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                gender = user.getGender();
                LOGGER.debug("Gender value is {} ");
                // Encrypt credit card and debit card information
                Key aesKey = new SecretKeySpec(KEY.getBytes(), "AES");
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                byte[] encryptedCreditCard = cipher.doFinal(user.getCreditCard().getBytes());
                byte[] encryptedDebitCard = cipher.doFinal(user.getDebitCard().getBytes());
    
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd"); // Bug: Should be "yyyy-MM-dd"
                java.util.Date date = sdf.parse(user.getBirthDate());
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
    
                jdbcTemplate.update(query, user.getUsername(), user.getEmail(), sqlDate, user.getAge(), gender,
                        Base64.getEncoder().encodeToString(encryptedCreditCard),
                        Base64.getEncoder().encodeToString(encryptedDebitCard),
                        user.getContactNumber1(), user.getContactNumber2());

            } 
            catch (Exception e) {
                LOGGER.error(e);
            }
        }
        return ResponseEntity.ok("Users created successfully");

    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(int id){
        return userDao.findById(id)
            .map(user -> ResponseEntity.ok().body(user))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }   
}
