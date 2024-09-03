package com.techtalenthub.controller;

import com.techtalenthub.model.Job;
import com.techtalenthub.service.JobService;
import com.techtalenthub.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {

  private final JobService jobService;

  @Autowired
  public JobController(JobService jobService) {
    this.jobService = jobService;
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
  public ResponseEntity<Job> findById(@PathVariable Long id) {
    Optional<Job> optionalJob = jobService.findById(id);

    if (optionalJob.isPresent()) {
      return ResponseEntity.ok(optionalJob.get());
    } else {
      throw new ResourceNotFoundException("Job not found with id " + id);
    }
  }

  /**
   * Cria uma nova vaga de emprego.
   * 
   * @param newJob a vaga de emprego a ser criada
   * @return a vaga de emprego criada
   */
  @PostMapping
  public ResponseEntity<Job> create(@RequestBody Job newJob) {
    Job createdJob = jobService.save(newJob);

    return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
  }

  /**
   * Atualiza uma vaga de emprego existente.
   * 
   * @param id o ID da vaga de emprego a ser atualizada
   * @param newJob os dados de atualização da vaga de emprego
   * @return a vaga de emprego atualizada ou uma resposta de erro
   */
  @PutMapping("/{id}")
  public ResponseEntity<Job> update(@PathVariable Long id, @RequestBody Job newJob) {
    Optional<Job> optionalJob = jobService.findById(id);

    if (!optionalJob.isPresent()) {
      throw new ResourceNotFoundException("Job not found with id " + id);
    }

    Job updatedJob = jobService.update(optionalJob.get(), newJob);

    return ResponseEntity.ok(updatedJob);
  }

  /**
   * Deleta uma vaga de emprego pelo ID.
   * 
   * @param id o ID da vaga de emprego a ser deletada
   * @return uma resposta de sucesso ou erro
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if (!jobService.existsById(id)) {
      throw new ResourceNotFoundException("Job not found with id " + id);
    }

    jobService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
