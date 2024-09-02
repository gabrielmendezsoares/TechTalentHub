package com.techtalenthub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techtalenthub.model.Job;
import com.techtalenthub.repository.JobRepository;
import com.techtalenthub.exception.ResourceNotFoundException;

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

  public List<Job> findAll() {
    return jobRepository.findAll();
  }

  public Optional<Job> findById(Long id) {
    return jobRepository.findById(id);
  }

  public Job save(Job job) {
    return jobRepository.save(job);
  }

  public Job update(Long id, Job job) {
    if (!jobRepository.existsById(id)) {
      throw new ResourceNotFoundException("Job not found with id " + id);
    }

    job.setId(id);

    return jobRepository.save(job);
  }

  public void delete(Long id) {
    if (!jobRepository.existsById(id)) {
      throw new ResourceNotFoundException("Job not found with id " + id);
    }

    jobRepository.deleteById(id);
  }

  public boolean existsById(Long id) {
    return jobRepository.existsById(id);
  }
}
