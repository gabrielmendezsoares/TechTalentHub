package com.techtalenthub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Application {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "job_id", nullable = false)
  private Job job;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private LocalDateTime applicationDate;

  public Long getId() {
    return this.id;
  }

  public User getUser() {
    return this.user;
  }

  public Job getJob() {
    return this.job;
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
}
