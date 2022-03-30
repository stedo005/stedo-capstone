package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void createUser(UserData userData) {
        userRepository.save(userData);
    }

    public Optional <UserData> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
