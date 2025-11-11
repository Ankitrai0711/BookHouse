package com.example.bookstore.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.entity.AuthenticationResponse;
import com.example.bookstore.entity.User;
import com.example.bookstore.repository.UserRepository;
import com.example.bookstore.service.UserService;
import com.example.bookstore.util.JwtUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        user.setRole("ROLE_"+user.getRole().toUpperCase().toString());
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

   


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User login) {
        Optional<User> userOptional = userService.login(login.getEmail(), login.getPassword());
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
            
            AuthenticationResponse response = new AuthenticationResponse(token, user);
            
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    
    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable Long id) {
    	return userRepository.findById(id); 
    }
}

