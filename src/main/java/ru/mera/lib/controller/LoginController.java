package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.User;
import ru.mera.lib.repository.UserRepository;
import ru.mera.lib.service.TokenHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenHandler tokenHandler;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String name, @RequestParam(required = false) String password){

        Optional<User> opUser = userRepository.findByUsername(name);
        if(opUser.isPresent()){
            User user = opUser.get();
            System.out.println(user.toString());
            if (bCryptPasswordEncoder.matches(password, user.getPassword())){
                return tokenHandler.generateAccessToken(user.getId(), LocalDateTime.now().plusDays(14));
            } else throw new UsernameNotFoundException("User " + name + " not found!");
        } else throw new UsernameNotFoundException("User " + name + " not found!");
    }
}
