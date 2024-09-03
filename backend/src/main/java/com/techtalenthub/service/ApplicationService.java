package com.techtalenthub.service;

import com.techtalenthub.model.Application;
import com.techtalenthub.repository.ApplicationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApplicationService {

  private final ApplicationRepository applicationRepository;

  @Autowired
  public ApplicationService(ApplicationRepository applicationRepository) {
    this.applicationRepository = applicationRepository;
  }

  /**
   * Retorna todas as aplicações.
   * 
   * @return lista de todas as aplicações
   */
  public List<Application> findAll() {
    return applicationRepository.findAll();
  }

  /**
   * Encontra uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação
   * @return um Optional contendo a aplicação, se encontrada
   */
  public Optional<Application> findById(Long id) {
    return applicationRepository.findById(id);
  }

  /**
   * Salva uma nova aplicação.
   * 
   * @param newApplication a aplicação a ser salva
   * @return a aplicação salva
   */
  public Application save(Application newApplication) {
    return applicationRepository.save(newApplication);
  }

  /**
   * Atualiza uma aplicação existente.
   * 
   * @param application a aplicação a ser atualizada
   * @param newApplication os dados de atualização da aplicação
   * @return a aplicação atualizada
   */
  public Application update(Application application, Application newApplication) {
    application.setStatus(newApplication.getStatus());

    return applicationRepository.save(application);
  }

  /**
   * Deleta uma aplicação pelo ID.
   * 
   * @param id o ID da aplicação a ser deletada
   */
  public void delete(Long id) {
    applicationRepository.deleteById(id);
  }

  /**
   * Verifica se uma aplicação existe pelo ID.
   * 
   * @param id o ID da aplicação
   * @return true se a aplicação existir, false caso contrário
   */
  public boolean existsById(Long id) {
    return applicationRepository.existsById(id);
  }
}
