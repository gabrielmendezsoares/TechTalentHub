package com.techtalenthub.controller;

import com.techtalenthub.model.Job;
import com.techtalenthub.model.User;
import com.techtalenthub.dto.UpdateApplicationRequest;
import com.techtalenthub.service.JobService;
import com.techtalenthub.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

  private final JobService jobService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public JobController(JobService jobService, PasswordEncoder passwordEncoder) {
    this.jobService = jobService;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Retorna todas as vagas de emprego.
   * 
   * @return lista de todas as vagas de emprego
   */
  @GetMapping
  public ResponseEntity<List<Job>> findAll() {
    List<Job> jobs = jobService.findAll();
    
    return ResponseEntity.ok(jobs);
  }

  /**
   * Encontra uma vaga de emprego pelo ID.
   * 
   * @param id o ID da vaga de emprego
   * @return a vaga de emprego encontrada ou uma resposta de erro
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    Optional<Job> job = jobService.findById(id);
    
    if (job.isPresent()) {
      return ResponseEntity.ok(job.get());
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found with id " + id);
    }
  }

  /**
   * Cria uma nova vaga de emprego.
   * 
   * @param job a vaga de emprego a ser criada
   * @param request os dados da requisição
   * @return a vaga de emprego criada
   */
  @PostMapping
  public ResponseEntity<?> create(@RequestBody Job job, @RequestBody UpdateApplicationRequest request) {
    if ("admin".equals(request.getRole())) {
      Job createdJob = jobService.save(job);
      
      return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body("Forbidden: Invalid credentials or insufficient permissions");
    }
  }

  /**
   * Atualiza uma vaga de emprego existente.
   * 
   * @param id o ID da vaga de emprego a ser atualizada
   * @param job os dados de atualização da vaga de emprego
   * @param request os dados da requisição
   * @return a vaga de emprego atualizada ou uma resposta de erro
   */
  @PutMapping("/{id}")
  public ResponseEntity<Job> update(@PathVariable Long id, @RequestBody Job job, @RequestBody UpdateApplicationRequest request) {
    if ("admin".equals(request.getRole())) {
      Optional<Job> job = jobService.findById(id);
      
      if (job.isPresent()) {
        Job updatedJob = jobService.update(id, job);
        
        return ResponseEntity.ok(updatedJob);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found with id " + id);
      }
  }

  /**
   * Deleta uma vaga de emprego pelo ID.
   * 
   * @param id o ID da vaga de emprego a ser deletada
   * @param request os dados da requisição
   * @return uma resposta de sucesso ou erro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Long id, @RequestBody UpdateApplicationRequest request) {
    if ("admin".equals(request.getRole())) {
      Optional<Job> job = jobService.findById(id);
      
      if (job.isPresent()) {
        jobService.delete(id);
        
        return ResponseEntity.noContent().build();
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Job not found with id " + id);
      }
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden: Invalid credentials or insufficient permissions"););
    }
  }
}
