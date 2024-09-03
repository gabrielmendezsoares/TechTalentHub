package com.techtalenthub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "app_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  @NotNull(message = "O email não pode ser nulo")
  @Email(message = "Email inválido")
  @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Formato de email inválido")
  private String email;

  @Column(nullable = false)
  @NotNull(message = "A senha não pode ser nula")
  @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
  private String password;

  @Column(nullable = false)
  @NotNull(message = "O nome não pode ser nulo")
  @Size(min = 2, max = 50, message = "O nome deve ter entre 2 e 50 caracteres")
  private String name;

  @Column(nullable = false)
  @NotNull(message = "A função não pode ser nula")
  @Pattern(regexp = "^(ADMIN|USER)$", message = "A função deve ser 'ADMIN' ou 'USER'")
  private String role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Application> applications;

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getRole() {
    return this.role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public List<Application> getApplications() {
    return this.applications;
  }

  public void setApplications(List<Application> applications) {
    this.applications = applications;
  }
}
