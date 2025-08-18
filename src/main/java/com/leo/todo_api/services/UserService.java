package com.leo.todo_api.services;

import com.leo.todo_api.models.User;
import com.leo.todo_api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  @PostConstruct
  public void init() {
    //System.out.println("UserService initialized with encoder: " + encoder.getClass().getName());
    System.out.println("UserService initialized");
  }

  public boolean login(String email, String rawPassword) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      return false; // User not found
    }
    // For now, just check if the rawPassword matches the stored password directly
    // (In a real app, you should NEVER do this—always use password encoding!)
    return rawPassword.equals(user.getPassword());
  }


  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id);
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public Optional<User> updateUser(Long id, User userDetails) {
    return userRepository.findById(id).map(user -> {
      user.setUsername(userDetails.getUsername());
      user.setEmail(userDetails.getEmail());
      user.setRole(userDetails.getRole());
      if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
        user.setPassword(userDetails.getPassword());
      }
      return userRepository.save(user);
    });
  }


  public boolean deleteUser(Long id) {
    return userRepository.findById(id).map(user -> {
      userRepository.delete(user);
      return true;
    }).orElse(false);
  }
}
