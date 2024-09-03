package com.techtalenthub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Job {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  @NotNull(message = "O título não pode ser nulo")
  @Size(min = 2, message = "O título deve ter pelo menos 2 caracteres")
  private String title;

  @Column(nullable = false)
  @NotNull(message = "A descrição não pode ser nula")
  @Size(min = 10, message = "A descrição deve ter pelo menos 10 caracteres")
  private String description;

  @Column(nullable = false)
  @NotNull(message = "A empresa não pode ser nula")
  @Size(min = 2, message = "O nome da empresa deve ter pelo menos 2 caracteres")
  private String company;

  @Column(nullable = false)
  @NotNull(message = "A localização não pode ser nula")
  @Size(min = 2, message = "A localização deve ter pelo menos 2 caracteres")
  private String location;

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCompany() {
    return this.company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public String getLocation() {
    return this.location;
  }

  public void setLocation(String location) {
    this.location = location;
  }
}
