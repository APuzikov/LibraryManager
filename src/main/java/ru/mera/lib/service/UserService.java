package ru.mera.lib.service;

import com.google.inject.internal.util.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;
import java.util.Optional;

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

        if (roleRepository.findByName("user") == null) {
            Role role = new Role();
            role.setName("user");
            roleRepository.save(role);
        }

        if (roleRepository.findByName("admin") == null) {
            Role role = new Role();
            role.setName("admin");
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
            userRoles.setRoleId(1);
            userRolesRepository.save(userRoles);

            user.setAuthorities(ImmutableList.of(roleRepository.findByName("admin")));
        }
    }

    public OperationStatus saveUser(User user) {
        try {
            Assert.notNull(user, "User can't be null!");
            Assert.hasText(user.getUsername(), "Name is empty!");
            Assert.hasText(user.getPassword(), "Password is empty!");
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.getOne(1));        //добавляем роль user
            user.setAuthorities(roles);
            userRepository.save(user);
            return new OperationStatus(true,"User successfully saved!");
        } catch (Exception e){
            return new OperationStatus(false, "Can't save user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public OperationStatus removeUser(int id) {

        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) {
            User user = opUser.get();
            try {
                userRepository.delete(user);
                return new OperationStatus(true,"User successfully deleted!");
            } catch (Exception e){
                return new OperationStatus(false,"Can't delete user: " + e.getMessage());
            }
        }
        return new OperationStatus(false,"This user in't exist!");
    }

    public User getOneUser(int id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) return opUser.get();
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found!"));

//        List<UserRoles> userRoles = userRolesRepository.findByUserId(user.getId());
//        List<Role> roles = new ArrayList<>();
//
//        for (UserRoles userRole : userRoles){
//            Role role = roleRepository.getOne(userRole.getRoleId());
//            roles.add(role);
//        }
//
//        user.setAuthorities(roles);
        return user;
    }

    public Optional<User> findById(int id){
        return userRepository.findById(id);
    }
}
