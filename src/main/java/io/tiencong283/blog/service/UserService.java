package io.tiencong283.blog.service;

import io.tiencong283.blog.model.WebUser;
import io.tiencong283.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUser authUser = this.userRepository.findByUsername(username);
        if (authUser != null){
            authUser.createUser();
        } else {
            throw new UsernameNotFoundException(String.format("cannot find the user with username '%s'", username));
        }
        return authUser;
    }

    public Optional<WebUser> getUserByUserID(int userID) {
        return this.userRepository.findByUserID(userID);
    }

    public List<WebUser> getAllUsers() {
        return this.userRepository.findAll();
    }

    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public void addUser(WebUser webUser){
        this.userRepository.saveAndFlush(webUser);
    }
}
