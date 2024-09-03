package com.techtalenthub.controller;

import com.techtalenthub.model.Application;
import com.techtalenthub.service.ApplicationService;
import com.techtalenthub.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

  private final ApplicationService applicationService;

  @Autowired
  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

  /**
   * Retorna todas as aplicações.
   * 
   * @return lista de todas as aplicações
   */
  @GetMapping
  public ResponseEntity<List<Application>> findAll() {
    List<Application> applications = applicationService.findAll();

    return ResponseEntity.ok(applications);
  }

  /**
   * Encontra uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação
   * @return a aplicação encontrada ou uma resposta de erro
   */
  @GetMapping("/{id}")
  public ResponseEntity<Application> findById(@PathVariable Long id) {
    Optional<Application> optionalApplication = applicationService.findById(id);

    if (optionalApplication.isPresent()) {
      return ResponseEntity.ok(optionalApplication.get());
    } else {
      throw new ResourceNotFoundException("Application not found with id " + id);
    }
  }

  /**
   * Cria uma nova aplicação.
   * 
   * @param newApplication a aplicação a ser criada
   * @return a aplicação criada
   */
  @PostMapping
  public ResponseEntity<Application> create(@RequestBody Application newApplication) {
    Application createdApplication = applicationService.save(newApplication);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdApplication);
  }

  /**
   * Atualiza uma aplicação existente.
   * 
   * @param id o ID da aplicação a ser atualizada
   * @param updatedApplication os dados de atualização da aplicação
   * @return a aplicação atualizada ou uma resposta de erro
   */
  @PutMapping("/{id}")
  public ResponseEntity<Application> update(@PathVariable Long id, @RequestBody Application newApplication) {
    Optional<Application> optionalApplication = applicationService.findById(id);

    if (!optionalApplication.isPresent()) {
      throw new ResourceNotFoundException("Application not found with id " + id);
    }

    Application updatedApplication = applicationService.update(optionalApplication.get(), newApplication);

    return ResponseEntity.ok(updatedApplication);
  }

  /**
   * Deleta uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação a ser deletada
   * @return uma resposta de sucesso ou erro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!applicationService.existsById(id)) {
      throw new ResourceNotFoundException("Application not found with id " + id);
    }

    applicationService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
