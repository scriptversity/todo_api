package com.leo.todo_api.controllers;

import com.leo.todo_api.models.User;
import com.leo.todo_api.services.JwtService;
import com.leo.todo_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private final UserService userService;
  private final JwtService jwtService;

  public UserController(UserService userService, JwtService jwtService){
    this.userService = userService;
    this.jwtService = jwtService;
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/register")
  public ResponseEntity<User> createUser(@RequestBody User user) {
    return ResponseEntity.ok(userService.createUser(user));
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUser(id, user)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    boolean deleted = userService.deleteUser(id);
    return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody User user) {
    boolean success = userService.login(user.getEmail(), user.getPassword());
    if (!success) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    String accessToken = jwtService.generateAccessToken(user.getEmail());
    String refreshToken = jwtService.generateRefreshToken(user.getEmail());

    return ResponseEntity.ok(Map.of(
            "accessToken", accessToken,
            "refreshToken", refreshToken
    ));
  }
}
