package com.example.demo.service;


import com.example.demo.entities.MyUser;
import org.springframework.security.core.userdetails.User;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    private final UserRepository UserRepository;

    // Constructor injection for UserRepository
    public MyUserDetailService(UserRepository myUserRepository) {
        this.UserRepository = myUserRepository;
    }

    /**
     * Loads the user by username.
     *
     * @param username the username to search for
     * @return the UserDetails object
     * @throws UsernameNotFoundException if the username is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> userOptional = UserRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            MyUser user = userOptional.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().split(","))
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}