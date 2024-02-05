package com.Apiwiz.taskmanagementapi.config;

import com.Apiwiz.taskmanagementapi.models.User;
import com.Apiwiz.taskmanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String userEmailId) throws UsernameNotFoundException {
        Optional<User> optionalUser=userRepository.findByEmailId(userEmailId);
        if(!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid Username or password");
        }
        User user=optionalUser.get();
        return new UserDetailsCreator(user);
    }
}
