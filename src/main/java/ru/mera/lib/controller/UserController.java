package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.User;
import ru.mera.lib.repository.UserRepository;
import ru.mera.lib.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v.1.0/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody User user){

        try{
            List<String> roles = new ArrayList<>();
            roles.add("USER");
            user.setRoles(roles);
            userService.create(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}