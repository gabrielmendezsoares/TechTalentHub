package com.techtalenthub.controller;

import com.techtalenthub.model.Application;
import com.techtalenthub.model.User;
import com.techtalenthub.dto.UpdateApplicationRequest;
import com.techtalenthub.exception.ResourceNotFoundException;
import com.techtalenthub.service.ApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

  private final ApplicationService applicationService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public ApplicationController(ApplicationService applicationService, PasswordEncoder passwordEncoder) {
    this.applicationService = applicationService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Retorna todas as aplicações.
   * 
   * @return lista de todas as aplicações
   */
  @GetMapping
  public ResponseEntity<?> findAll(@RequestBody UpdateApplicationRequest request) {
    if ("admin".equals(request.getRole())) {
      List<Application> applications = applicationService.findAll();

      return ResponseEntity.ok(applications);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Forbidden: Invalid credentials or insufficient permissions");
    }
  }

  /**
   * Encontra uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação
   * @return a aplicação encontrada ou uma resposta de erro
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id, @RequestBody UpdateApplicationRequest request) {
    Application application = applicationService.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Application not found with id " + id));

    User user = application.getUser();

    if (isAuthorized(user, request)) {
      return ResponseEntity.ok(application);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Forbidden: Invalid credentials or insufficient permissions");
    }
  }

  /**
   * Cria uma nova aplicação.
   * 
   * @param application a aplicação a ser criada
   * @return a aplicação criada
   */
  @PostMapping
  public ResponseEntity<Application> create(@RequestBody Application application) {
    Application createdApplication = applicationService.save(application);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
  }

  /**
   * Atualiza uma aplicação existente.
   * 
   * @param id      o ID da aplicação a ser atualizada
   * @param request os dados da requisição
   * @return a aplicação atualizada ou uma resposta de erro
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateApplicationRequest request) {
    if (!applicationService.existsById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    if ("admin".equals(request.getRole())) {
      Application application = applicationService.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException("Application not found with id " + id));

      Application updatedApplication = applicationService.update(id, request, application);

      return ResponseEntity.ok(updatedApplication);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Forbidden: Invalid credentials or insufficient permissions");
    }
  }

  /**
   * Deleta uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação a ser deletada
   * @return uma resposta de sucesso ou erro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody UpdateApplicationRequest request) {
    if ("admin".equals(request.getRole())) {
      if (!applicationService.existsById(id)) {
        throw new ResourceNotFoundException("Application not found with id " + id);
      }

      applicationService.delete(id);

      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Forbidden: Invalid credentials or insufficient permissions");
    }
  }

  /**
   * Verifica se o usuário está autorizado.
   * 
   * @param user    o usuário da aplicação
   * @param request os dados da requisição
   * @return true se autorizado, false caso contrário
   */
  private boolean isAuthorized(User user, UpdateApplicationRequest request) {
    return user.getEmail().equals(request.getEmail()) &&
        passwordEncoder.matches(request.getPassword(), user.getPassword()) ||
        "admin".equals(request.getRole());
  }
}
