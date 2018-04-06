package ru.mera.lib.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.User;
import ru.mera.lib.model.LoginForm;
import ru.mera.lib.repository.UserRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

        @PostMapping("/login")
        public ResponseEntity<Object> login(@RequestBody LoginForm loginForm, HttpServletResponse response) throws IOException {

            User user = userRepository.findByUsername(loginForm.getUsername());

            if (user != null && bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword())) {
                String token = Jwts.builder().setSubject(loginForm.getUsername()).claim("roles", user.getRoles()).setIssuedAt(new Date())
                        .signWith(SignatureAlgorithm.HS256, "secretkey").compact();
                response.addHeader("Authorization", "Bearer " + token);
                return new ResponseEntity<>( HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

        }
}
