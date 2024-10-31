package com.humanbooster.authent.services;

import com.humanbooster.authent.models.User;
import com.humanbooster.authent.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<User> getAll(){
        return this.userRepository.findAll();
    }

    public User findByEmail(String email){
        return this.userRepository.findByEmail(email);
    };

//    public User findByUsername(String username){
//        return this.userRepository.findByUsername(username);
//    };

    public User saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

//    public List<User> findByEmailWithoutId(String username, Long id){
//        return this.userRepository.findByUsernameWithoutId(username, id);
//    }

    public void removeUser(User user) {
        this.userRepository.delete(user);
    }
}
