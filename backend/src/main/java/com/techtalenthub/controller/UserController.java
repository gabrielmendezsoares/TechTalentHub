package com.techtalenthub.controller;

import com.techtalenthub.model.User;
import com.techtalenthub.service.UserService;
import com.techtalenthub.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Retorna todos os usuários.
   * 
   * @return lista de todos os usuários
   */
  @GetMapping
  public ResponseEntity<List<User>> findAll() {
    List<User> users = userService.findAll();

    return ResponseEntity.ok(users);
  }

  /**
   * Encontra um usuário pelo ID.
   * 
   * @param id o ID do usuário
   * @return o usuário encontrado ou uma resposta de erro
   */
  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    Optional<User> optionalUser = userService.findById(id);

    if (optionalUser.isPresent()) {
      return ResponseEntity.ok(optionalUser.get());
    } else {
      throw new ResourceNotFoundException("User not found with id " + id);
    }
  }

  /**
   * Encontra um usuário pelo email.
   * 
   * @param email o email do usuário
   * @return o usuário encontrado ou uma resposta de erro
   */
  @GetMapping("/email/{email}")
  public ResponseEntity<User> findByEmail(@PathVariable String email) {
    Optional<User> optionalUser = userService.findByEmail(email);

    if (optionalUser.isPresent()) {
      return ResponseEntity.ok(optionalUser.get());
    } else {
      throw new ResourceNotFoundException("User not found with email " + email);
    }
  }

  /**
   * Cria um novo usuário.
   * 
   * @param newUser o usuário a ser criado
   * @return o usuário criado
   */
  @PostMapping
  public ResponseEntity<User> create(@RequestBody User newUser) {
    User createdUser = userService.save(newUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  /**
   * Atualiza um usuário existente.
   * 
   * @param id          o ID do usuário a ser atualizado
   * @param updatedUser os dados de atualização do usuário
   * @return o usuário atualizado ou uma resposta de erro
   */
  @PutMapping("/{id}")
  public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User newUser) {
    Optional<User> optionalUser = userService.findById(id);

    if (!optionalUser.isPresent()) {
      throw new ResourceNotFoundException("User not found with id " + id);
    }

    User updatedUser = userService.update(optionalUser.get(), newUser);

    return ResponseEntity.ok(updatedUser);
  }

  /**
   * Deleta um usuário pelo ID.
   * 
   * @param id o ID do usuário a ser deletado
   * @return uma resposta de sucesso ou erro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!userService.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id " + id);
    }

    userService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
