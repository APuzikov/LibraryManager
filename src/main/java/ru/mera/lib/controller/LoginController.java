package ru.mera.lib.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.mera.lib.entity.Role;
import ru.mera.lib.entity.User;
import ru.mera.lib.entity.UserRoles;
import ru.mera.lib.model.LoginForm;
import ru.mera.lib.repository.RoleRepository;
import ru.mera.lib.repository.UserRepository;
import ru.mera.lib.repository.UserRolesRepository;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static ru.mera.lib.security.SecurityConstants.*;


@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public String authorization(@RequestBody LoginForm loginForm, HttpServletResponse res){

        Optional<User> opUser = userRepository.findByUsername(loginForm.getUsername());

        if(opUser.isPresent()){
            User user = opUser.get();

            List<UserRoles> userRoles = userRolesRepository.findByUserId(user.getId());
            List<Role> roles = new ArrayList<>();
            for (UserRoles userRole : userRoles){
                Role role = new Role();
                role.setName(roleRepository.getOne(userRole.getRoleId()).getName());
                roles.add(role);
            }
            user.setAuthorities(roles);

            System.out.println(user.toString());

            if (bCryptPasswordEncoder.matches(loginForm.getPassword(), user.getPassword())){
                String token = Jwts.builder()
                        .setSubject(user.getUsername())
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                        .compact();
                System.out.println(token);
                res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
                return token;
            } else throw new UsernameNotFoundException("User " + loginForm.getUsername() + " not found!");
        } else throw new UsernameNotFoundException("User " + loginForm.getUsername() + " not found!");
    }
}
