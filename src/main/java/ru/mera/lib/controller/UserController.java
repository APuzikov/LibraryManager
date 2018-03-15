package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.JsonResponse;
import ru.mera.lib.entity.User;
import ru.mera.lib.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v.1.0")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public JsonResponse saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PutMapping("/updateUser/{id}")
    public JsonResponse updateUser(@PathVariable int id, @RequestBody User user){
        user.setId(id);
        return userService.saveUser(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getUser/{id}")
    public User getOneUser(@PathVariable int id){
        return userService.getOneUser(id);
    }

    @DeleteMapping("/deleteUser/{id}")
    public JsonResponse removeUser(@PathVariable int id) {
        return userService.removeUser(id);
    }

    @PostMapping("/authenticate")
    public JsonResponse checkIfPresent(@RequestBody User user){
        return userService.checkUser(user);
    }
}