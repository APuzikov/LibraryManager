package ru.mera.lib.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.OperationStatus;
import ru.mera.lib.entity.User;
import ru.mera.lib.entity.UserRoles;
import ru.mera.lib.repository.UserRepository;
import ru.mera.lib.repository.UserRolesRepository;
import ru.mera.lib.service.UserService;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v.1.0")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @PostMapping("/createUser")
    public String saveUser(@RequestBody User user){

        UserRoles userRoles = new UserRoles();

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setEnable(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);

        User savedUser = userRepository.save(user);

        userRoles.setUserId(savedUser.getId());
        userRoles.setRoleId(1);
        userRolesRepository.save(userRoles);

        return "----------------------------------------";
    }

//    @PutMapping("/updateUser/{id}")
//    public OperationStatus updateUser(@PathVariable int id, @RequestBody User user){
//        user.setId(id);
//        return userService.saveUser(user);
//    }
//
//    @GetMapping("/getAllUsers")
//    public List<User> getAllUsers(){
//        return userService.getAllUsers();
//    }
//
//    @GetMapping("/getUser/{id}")
//    public User getOneUser(@PathVariable int id){
//        return userService.getOneUser(id);
//    }
//
//    @DeleteMapping("/deleteUser/{id}")
//    public OperationStatus removeUser(@PathVariable int id) {
//        return userService.removeUser(id);
//    }


}