package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.User;
import ru.mera.lib.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public String saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("/all")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getOneUser(@PathVariable int id){
        return userService.getOneUser(id);
    }

    @GetMapping("/remove/{id}")
    public String removeUser(@PathVariable int id) {
        return userService.removeUser(id);
    }
}