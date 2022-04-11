package com.example.demo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    public void createUser(@RequestBody UserData userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        userData.setLastUpdate("2000-01-01");
        userService.createUser(userData);
    }

    @GetMapping("/{username}")
    public UserData getUser(@PathVariable String username){
        if(userService.findByUsername(username).isPresent()){
            return userService.findByUsername(username).get();
        }
        throw new IllegalArgumentException("User doesnt exist");
    }

}
