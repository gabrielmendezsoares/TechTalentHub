package com.techtalenthub.controller;

import com.techtalenthub.model.User;
import com.techtalenthub.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    List<User> users = userService.findAll();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    Optional<User> user = userService.findById(id);

    return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping
  public ResponseEntity<User> create(@RequestBody User user) {
    User createdUser = userService.save(user);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
    if (!userService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    User updatedUser = userService.update(id, user);

    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!userService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
