package ru.mera.lib.service;

import com.google.inject.internal.util.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.OperationStatus;
import ru.mera.lib.entity.Role;
import ru.mera.lib.entity.User;
import ru.mera.lib.entity.UserRoles;
import ru.mera.lib.repository.RoleRepository;
import ru.mera.lib.repository.UserRepository;
import ru.mera.lib.repository.UserRolesRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @PostConstruct
    public void init(){

        if (roleRepository.findByName("ROLE_USER") == null) {
            Role role = new Role();
            role.setName("ROLE_USER");
            roleRepository.save(role);
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.save(role);
        }

        if (!userRepository.findByUsername("trigger").isPresent()){
            User user = new User();
            UserRoles userRoles = new UserRoles();

            user.setUsername("trigger");
            user.setPassword(new BCryptPasswordEncoder().encode("1111"));
            user.setEnable(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            User savedUser = userRepository.save(user);

            userRoles.setUserId(savedUser.getId());
            userRoles.setRoleId(2);
            userRolesRepository.save(userRoles);

            user.setAuthorities(ImmutableList.of(roleRepository.findByName("ROLE_ADMIN")));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found!"));

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
