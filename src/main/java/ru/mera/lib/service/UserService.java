package ru.mera.lib.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.mera.lib.entity.User;
import ru.mera.lib.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String  saveUser(User user) {
        try {
            Assert.notNull(user, "User can't be null!");
            Assert.hasText(user.getName(), "Name is empty!");
            Assert.hasText(user.getPassword(), "Password is empty!");
            Assert.notNull(user.isEnable(), "Parameter 'enable' is null!");
            userRepository.save(user);
            return "User successfully saved!";
        } catch (Exception e){
            return "Can't save user: " + e.getMessage();
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public String removeUser(int id) {

        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) {
            User user = opUser.get();
            try {
                userRepository.delete(user);
                return "User successfully deleted!";
            } catch (Exception e){
                return "Can't delete user: " + e.getMessage();
            }
        }
        return "This user in't exist!";
    }

    public User getOneUser(int id) {
        Optional<User> opUser = userRepository.findById(id);
        if (opUser.isPresent()) return opUser.get();
        return null;
    }
}
