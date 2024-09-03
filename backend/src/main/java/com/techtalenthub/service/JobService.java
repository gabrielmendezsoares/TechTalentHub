package com.techtalenthub.service;

import com.techtalenthub.model.Job;
import com.techtalenthub.repository.JobRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobService {

  private final JobRepository jobRepository;

  @Autowired
  public JobService(JobRepository jobRepository) {
    this.jobRepository = jobRepository;
  }

  /**
   * Retorna todas as vagas de emprego.
   * 
   * @return lista de todas as vagas de emprego
   */
  public List<Job> findAll() {
    return jobRepository.findAll();
  }

  /**
   * Encontra uma vaga de emprego pelo ID.
   * 
   * @param id o ID da vaga de emprego
   * @return um Optional contendo a vaga de emprego, se encontrada
   */
  public Optional<Job> findById(Long id) {
    return jobRepository.findById(id);
  }

  /**
   * Salva uma nova vaga de emprego.
   * 
   * @param newJob a vaga de emprego a ser salva
   * @return a vaga de emprego salva
   */
  public Job save(Job newJob) {
    return jobRepository.save(newJob);
  }

  /**
   * Atualiza uma vaga de emprego existente.
   * 
   * @param job a vaga de emprego a ser atualizada
   * @param newJob os dados de atualização da vaga de emprego
   * @return a vaga de emprego atualizada
   */
  public Job update(Job job, Job newJob) {
    job.setTitle(newJob.getTitle());
    job.setDescription(newJob.getDescription());
    job.setCompany(newJob.getCompany());
    job.setLocation(newJob.getLocation());

    return jobRepository.save(job);
  }

  /**
   * Deleta uma vaga de emprego pelo ID.
   * 
   * @param id o ID da vaga de emprego a ser deletada
   */
  public void delete(Long id) {
    jobRepository.deleteById(id);
  }

  /**
   * Verifica se uma vaga de emprego existe pelo ID.
   * 
   * @param id o ID da vaga de emprego
   * @return true se a vaga de emprego existir, false caso contrário
   */
  public boolean existsById(Long id) {
    return jobRepository.existsById(id);
  }
}
