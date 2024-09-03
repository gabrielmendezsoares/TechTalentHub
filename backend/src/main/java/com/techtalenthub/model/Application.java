package com.techtalenthub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "application")
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @NotNull(message = "O usuário não pode ser nulo")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_id", nullable = false)
  @NotNull(message = "O trabalho não pode ser nulo")
  private Job job;

  @Column(nullable = false)
  @NotNull(message = "O status não pode ser nulo")
  @Pattern(regexp = "PENDING|UNDER_REVIEW|INTERVIEW_SCHEDULED|OFFER_MADE|HIRED|REJECTED", message = "Status inválido")
  private String status;

  @Column(nullable = false)
  @NotNull(message = "A data de aplicação não pode ser nula")
  private LocalDateTime applicationDate;

  public Long getId() {
    return this.id;
  }

  public User getUser() {
    return this.user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Job getJob() {
    return this.job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getApplicationDate() {
    return this.applicationDate;
  }

  public void setApplicationDate(LocalDateTime applicationDate) {
    this.applicationDate = applicationDate;
  }
}
